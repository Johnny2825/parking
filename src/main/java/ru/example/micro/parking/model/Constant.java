package ru.example.micro.parking.model;

/**
 * @author Tarkhov Evgeniy
 */
public class Constant {

    private Constant(){}

    public static class Kafka {

        private Kafka() {}
        public static final String MESSAGE_GUID_HEADER = "messageGuid";
    }


    public static class EmailMessageTemplate {
        private EmailMessageTemplate(){}

        public static final String USER_CREATE = "create";
        public static final String USER_UPDATED = "upd";
        public static final String USER_DELETED = "del";

        public static final String USER_CREATE_RESERVATION = "create";
        public static final String USER_UPDATED_RESERVATION = "update";
        public static final String USER_DELETE_RESERVATION = "delete";

        public static final String USER_START_PARKING = "start";
        public static final String USER_FINISH_PARKING = "finish";
    }


}
