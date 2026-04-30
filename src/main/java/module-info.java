module com.piggydoom {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.piggydoom to javafx.fxml;
    exports com.piggydoom;
}
