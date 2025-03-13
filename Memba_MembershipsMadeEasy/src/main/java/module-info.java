module org.michaelmathews.memba_membershipsmadeeasy {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.michaelmathews.memba_membershipsmadeeasy to javafx.fxml;
    exports org.michaelmathews.memba_membershipsmadeeasy;
}