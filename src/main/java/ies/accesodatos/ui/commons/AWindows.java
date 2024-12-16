package ies.accesodatos.ui.commons;

import javafx.scene.control.Alert;

import java.util.function.BiConsumer;

public abstract class AWindows implements IWindows {
    protected Router router;

    protected BiConsumer<String,Object> onaction;



    public AWindows() {


    }
    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    protected void showMessage(String message,String head, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(head);
        alert.setHeaderText(null);  // Puedes establecer un encabezado aquí si lo deseas
        alert.setContentText(message);

        alert.showAndWait();
    }
    public void setOnAction(BiConsumer<String,Object> onaction){
        this.onaction=onaction;
    }

}
