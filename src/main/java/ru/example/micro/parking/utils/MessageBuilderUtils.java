package ru.example.micro.parking.utils;


import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_CREATE;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_CREATE_RESERVATION;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_DELETED;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_DELETE_RESERVATION;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_FINISH_PARKING;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_START_PARKING;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_UPDATED;
import static ru.example.micro.parking.model.Constant.EmailMessageTemplate.USER_UPDATED_RESERVATION;

/**
 * @author Tarkhov Evgeniy
 */
public class MessageBuilderUtils {

    private MessageBuilderUtils(){}

    public static String messageUserAccountCreate(String userName) {
        return String.format(USER_CREATE, userName);
    }

    public static String messageUserAccountUpdate(String userName) {
        return String.format(USER_UPDATED, userName);
    }

    public static String messageUserAccountDelete(String userName) {
        return String.format(USER_DELETED, userName);
    }

    public static String messageUserCreateReservation(String userName,
                                                      Long reservationId,
                                                      String parkingSpaceId,
                                                      String timeFrom,
                                                      String timeTo) {
        return String.format(USER_CREATE_RESERVATION, userName, reservationId, parkingSpaceId, timeFrom, timeTo);
    }

    public static String messageUserUpdateReservation(String userName,
                                                      Long reservationId,
                                                      String parkingSpaceId,
                                                      String timeFrom,
                                                      String timeTo) {
        return String.format(USER_UPDATED_RESERVATION, userName, reservationId, parkingSpaceId, timeFrom, timeTo);
    }

    public static String messageUserDeleteReservation(String userName,
                                                      Long reservationId,
                                                      String parkingSpaceId,
                                                      String timeFrom,
                                                      String timeTo) {
        return String.format(USER_DELETE_RESERVATION, userName, reservationId, parkingSpaceId, timeFrom, timeTo);
    }

    public static String messageUserStartParking(String userName, String parkingSpaceCode, String actionTime) {
        return String.format(USER_START_PARKING, userName, parkingSpaceCode, actionTime);
    }

    public static String messageUserFinishParking(String userName, String parkingSpaceCode, String actionTime) {
        return String.format(USER_FINISH_PARKING, userName, parkingSpaceCode, actionTime);
    }


}
