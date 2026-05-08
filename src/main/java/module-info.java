module lk.ijse.serenity {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires jakarta.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;

    opens lk.ijse.serenity.entity to org.hibernate.orm.core;
    opens lk.ijse.serenity.config to jakarta.persistence;

    opens lk.ijse.serenity.controller to javafx.fxml;
    opens lk.ijse.serenity.dto.tm to javafx.base;

    exports lk.ijse.serenity;
}