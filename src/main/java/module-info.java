module com.shogrenjacobdev.librestock {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.shogrenjacobdev.librestock to javafx.fxml;
    exports com.shogrenjacobdev.librestock;
}