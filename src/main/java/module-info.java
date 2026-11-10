
module cl.duoc.pataverde {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports cl.duoc.pataverde;
    exports cl.duoc.pataverde.model;

    opens cl.duoc.pataverde to javafx.graphics;
    opens cl.duoc.pataverde.controller to javafx.fxml;
    opens cl.duoc.pataverde.model to com.google.gson;
}
