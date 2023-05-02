package ru.example.micro.parking.model;

import java.time.format.DateTimeFormatter;

/**
 * @author Tarkhov Evgeniy
 */
public class Constant {

    private Constant(){}

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

    public static class Kafka {

        private Kafka() {}
        public static final String MESSAGE_GUID_HEADER = "messageGuid";
    }


    public static class EmailMessageTemplate {
        private EmailMessageTemplate(){}

        public static final String USER_CREATE = "Здравствуйте, %s! Вы зарегистровались";
        public static final String USER_UPDATED = "Здравствуйте, %s! Был изменен Ваш профиль.";
        public static final String USER_DELETED = "Здравствуйте, %s! Ваш профиль был удалён.";

        public static final String USER_CREATE_RESERVATION = "Здравствуйте, %s! Вы создали бронь №%s на парковочное место %s на период %s - %s";
        public static final String USER_UPDATED_RESERVATION = "Здравствуйте, %s! Вы изменили бронь №%s на парковочное место %s на период %s - %s";
        public static final String USER_DELETE_RESERVATION = "Здравствуйте, %s! Вы удалили бронь №%s на парковочное место %s с %s по %s";

        public static final String USER_START_PARKING = "Здравствуйте, %s! Вы заняли парковочное место %s в %s";
        public static final String USER_FINISH_PARKING = "Здравствуйте, %s! Вы освободили парковочное место %s в %s";
    }


    public static class StatusCode {
        private StatusCode(){}

        public static final String NOT_VALID_DATA = "pp_nvd";
        public static final String PARKING_SPACE_NOT_EMPTY = "pp_psne";
        public static final String PARKING_SPACE_NOT_FOUND = "pp_psnf";
        public static final String TIME_CROSSING = "pp_t";
        public static final String USER_EXIST = "pp_ue";
        public static final String USER_NOT_FOUND = "pp_unf";
        public static final String USER_MISMATCH = "pp_um";



    }

}
