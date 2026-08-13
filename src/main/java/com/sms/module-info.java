module com.sms {

    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.sms to javafx.fxml;
    opens com.sms.model to com.google.gson;

    opens com.sms.controller to javafx.fxml;

    exports com.sms;
    exports com.sms.controller;
    exports com.sms.model;

}