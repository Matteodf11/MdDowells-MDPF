package ies.accesodatos.ui.empleados.view;


import ies.accesodatos.empleados.model.Empleado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.GridCell;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class EmpleadoCell extends GridCell<Empleado> {
    private Label label;
    private VBox vbox;
    private Consumer<Empleado> onClick;
    private Consumer<Empleado> onDoubleClick;
    private BiConsumer<Empleado, MouseEvent> onSecondaryClick;
    private ImageView image;
    private static Image noImage= new Image(EmpleadoCell.class.getResourceAsStream("/images/user2.png"));
    public EmpleadoCell() {
        super();
        // this.onClick = null;
        vbox = new VBox();
        label = new Label();
        image = new ImageView(noImage);
        this.vbox.getChildren().add(image);
        this.vbox.getChildren().add(label);

    }

    public void setOnClick(Consumer<Empleado> onClick) {
        this.onClick = onClick;
    }

    public void setOnDoubleClick(Consumer<Empleado> onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
    }

    public void setOnSecondaryClick(BiConsumer<Empleado, MouseEvent> onSecondaryClick) {
        this.onSecondaryClick = onSecondaryClick;

    }

    protected void updateItem(Empleado item, boolean empty) {

        if (empty || item == null) {
            setGraphic(null); // Si no hay contenido, no se muestra nada
        } else {


            this.vbox.setStyle("-fx-border-color: #eeeeee;-fx-border-radius: 5;-fx-border-insets: 5;-fx-border-width: 3;-fx-padding: 10 10 10 10;");//"-fx-border-style: dashed;");
            vbox.setAlignment(Pos.CENTER);
            this.vbox.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && onDoubleClick != null) {
                    onDoubleClick.accept(item);
                } else {

                    if (event.getButton() == MouseButton.SECONDARY) {
                        if (onSecondaryClick != null) {
                            onSecondaryClick.accept(item, event);
                        }
                    } else {
                        if (onClick != null) {
                            onClick.accept(null);
                        }
                    }
                }
            });

            if (item.getId() >= 0) {
                label.setText(item.getNombre());

            } else {
                label.setText("Nuevo");
            }
            label.setPadding(new Insets(10, 20, 10, 20));


            image.setFitWidth(vbox.getWidth() - 60);
            image.setPreserveRatio(true);
            if (item.getId() != -1) {


                if (!item.isActivo()) {

                    ColorAdjust grayscale = new ColorAdjust();
                    grayscale.setSaturation(-1.0);
                    image.setEffect(grayscale);

                }else{
                    image.setEffect(null);
                }
                this.image.setFitWidth(vbox.getWidth() - 60);
                this.image.setPreserveRatio(true);
                //en caso de no existir se pinta noimage
               // if(Files.exists(Path.of(CategoriaRepository.getPath()+item.getImg_src()))){
                //    this.image.setImage(new Image("file:"+ CategoriaRepository.getPath()+item.getImg_src(),96,96,true,true));
               // }
               // else {
                    image.setImage(noImage);
                //}
            }
            setGraphic(this.vbox); // Mostrar la imagen en la celda
        }
    }
}
