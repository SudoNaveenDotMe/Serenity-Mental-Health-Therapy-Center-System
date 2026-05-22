module lk.ijse.the_seranity_mental_health_therapy_center {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires static lombok;
    requires jbcrypt;

    opens lk.ijse.the_seranity_mental_health_therapy_center.entity to org.hibernate.orm.core;

    opens lk.ijse.the_seranity_mental_health_therapy_center to javafx.fxml;
    exports lk.ijse.the_seranity_mental_health_therapy_center;

    opens lk.ijse.the_seranity_mental_health_therapy_center.controller to javafx.fxml;
    exports lk.ijse.the_seranity_mental_health_therapy_center.controller;

    opens lk.ijse.the_seranity_mental_health_therapy_center.dto to javafx.base;

    exports lk.ijse.the_seranity_mental_health_therapy_center.util;
}