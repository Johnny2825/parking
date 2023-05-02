package ru.example.micro.parking.service.business.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.example.micro.parking.exception.UserExistException;
import ru.example.micro.parking.mapper.UserMapper;
import ru.example.micro.parking.model.dto.UserRequest;
import ru.example.micro.parking.model.dto.UserResponse;
import ru.example.micro.parking.repository.UserRepository;
import ru.example.micro.parking.service.tech.notification.mail.MailService;

import java.util.Optional;

import static ru.example.micro.parking.entity.QUserEntity.userEntity;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserAccountCreate;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserAccountDelete;
import static ru.example.micro.parking.utils.MessageBuilderUtils.messageUserAccountUpdate;


/**
 *  Сервис по работе с пользователями
 *
 * @author Tarkhov Evgeniy
 */
@Service
@RequiredArgsConstructor
public class UserServiceBase implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Override
    public Optional<UserResponse> findUserById(@NonNull final Long userId) {
        return userRepository.findById(userId).map(userMapper::toResponse);
    }

    @Override
    public Optional<UserResponse> createUser(@NonNull final UserRequest userRequest) {
        checkUserByEmail(userRequest);
        var userEntityForCreate = userMapper.toEntity(userRequest);
        userEntityForCreate.setActive();
        var userResponse = userMapper.toResponse(userRepository.save(userEntityForCreate));
        mailService.sendMessage(userRequest.getEmail(), messageUserAccountCreate(userRequest.getFirstName()));
        return Optional.of(userResponse);
    }

    @Override
    public Optional<UserResponse> updateUser(@NonNull final Long userId, @NonNull final UserRequest userRequest) {
        return userRepository.findById(userId).map(userEntity -> {
            if (!StringUtils.equalsIgnoreCase(userRequest.getEmail(), userEntity.getEmail())) {
                checkUserByEmail(userRequest);
            }
            userEntity.setFirstName(userRequest.getFirstName());
            userEntity.setLastName(userRequest.getLastName());
            userEntity.setEmail(userRequest.getEmail());    //TODO отдельная логика при обновление email
            userRepository.save(userEntity);
            var retVal = userMapper.toResponse(userEntity);
            mailService.sendMessage(userRequest.getEmail(), messageUserAccountUpdate(userRequest.getFirstName()));
            return retVal;
        });
    }

    @Override
    public Optional<UserResponse> deleteUser(@NonNull final Long userId) {
        return userRepository.findById(userId).map(userEntity -> {
            userEntity.softDeleted();
            userRepository.save(userEntity);
            var userResponse = userMapper.toResponse(userEntity);
            mailService.sendMessage(userResponse.getEmail(), messageUserAccountDelete(userResponse.getFirstName()));
            return userResponse;
        });
    }

    /**
     * Проверка существования пользователя по email
     * @param userRequest объект пользователя
     * @throws UserExistException если пользователь с переданным email уже существует
     */
    private void checkUserByEmail(@NonNull final UserRequest userRequest) throws UserExistException {
        var predicate = userEntity.email.eq(userRequest.getEmail());
        if (userRepository.findOne(predicate).isPresent()) {
            throw new UserExistException(userRequest.getEmail());
        }
    }
}
