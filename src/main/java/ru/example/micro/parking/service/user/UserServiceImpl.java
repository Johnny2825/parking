package ru.example.micro.parking.service.user;

import com.querydsl.core.types.Predicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.micro.parking.controller.dto.UserDto;
import ru.example.micro.parking.entity.QUserEntity;
import ru.example.micro.parking.entity.UserEntity;
import ru.example.micro.parking.exception.UserExistException;
import ru.example.micro.parking.mapper.UserMapper;
import ru.example.micro.parking.repository.UserRepository;
import ru.example.micro.parking.service.notification.mail.MailService;

import java.util.Optional;

import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_CREATE;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_DELETED;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_UPDATED;

/**
 * @author Tarkhov Evgeniy
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Override
    public Optional<UserDto> findUserById(@NonNull final Long userId) {
        return userRepository.findById(userId).map(userMapper::map);
    }

    @Override
    public Optional<UserDto> createUser(@NonNull final UserDto userDto) {
        checkUserByEmail(userDto);
        UserEntity userEntityForCreate = userMapper.map(userDto);
        userEntityForCreate.setActive();
        UserDto userDtoRetVal = userMapper.map(userRepository.save(userEntityForCreate));
        mailService.sendMessage(userDtoRetVal, USER_CREATE);
        return Optional.of(userDtoRetVal);
    }

    @Override
    @Transactional
    public Optional<UserDto> updateUser(@NonNull final Long userId, @NonNull final UserDto userDto) {
        return userRepository.findById(userId).map(userEntity -> {
            checkUserByEmail(userDto);
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            userEntity.setEmail(userDto.getEmail());
            UserDto retVal = userMapper.map(userEntity);
            mailService.sendMessage(retVal, USER_UPDATED);
            return retVal;
        });
    }

    /**
     * Проверка существования пользователя по email
     * @param userDto объект пользователя
     * @throws UserExistException исключение если пользователь существует
     */
    private void checkUserByEmail(@NonNull final UserDto userDto) throws UserExistException {
        Predicate predicate = QUserEntity.userEntity.email.eq(userDto.getEmail());
        if (userRepository.findOne(predicate).isPresent()) {
            log.info("Пользователь с таким адресом уже существует. Входящий объект: {}", userDto);
            throw new UserExistException(String.format("Пользователь с адресом %s уже существует", userDto.getEmail()));
        }
    }

    @Override
    @Transactional
    public Optional<UserDto> deleteUser(@NonNull final Long userId) {
        return userRepository.findById(userId).map(userEntity -> {
            userEntity.softDeleted();
            UserDto userDto = userMapper.map(userEntity);
            mailService.sendMessage(userDto, USER_DELETED);
            return userDto;
        });
    }
}
