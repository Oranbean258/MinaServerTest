module com.example.ms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires mina.core;
    requires lombok;
    requires org.slf4j;

    opens com.example.ms to javafx.fxml;
    exports com.example.ms;
}