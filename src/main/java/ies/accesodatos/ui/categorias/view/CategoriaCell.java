package ies.accesodatos.ui.categorias.view;



import ies.accesodatos.categorias.model.dto.CategoriaAgregateDTO;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class CategoriaCell extends GridCell<CategoriaAgregateDTO> {
    private Label label;
    private VBox vbox;
    private Consumer< CategoriaAgregateDTO> onClick;
    private Consumer< CategoriaAgregateDTO> onDoubleClick;
    private BiConsumer< CategoriaAgregateDTO, MouseEvent> onSecondaryClick;
    private ImageView image;
    private MFXFontIcon icon = new MFXFontIcon("", 16);
    private static Image noImage= new Image(CategoriaCell.class.getResourceAsStream("/images/No_image.png"));

    public CategoriaCell() {
        super();

        vbox = new VBox();
        label = new Label();





        image = new ImageView(noImage);

        this.vbox.getChildren().add(image);
        this.vbox.getChildren().add(label);

    }

    public void setOnClick(Consumer< CategoriaAgregateDTO> onClick) {
        this.onClick = onClick;
    }

    public void setOnDoubleClick(Consumer< CategoriaAgregateDTO> onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
    }

    public void setOnSecondaryClick(BiConsumer< CategoriaAgregateDTO, MouseEvent> onSecondaryClick) {
        this.onSecondaryClick = onSecondaryClick;

    }

    protected void updateItem( CategoriaAgregateDTO item, boolean empty) {

        if (empty || item == null) {
            setGraphic(null); // Si no hay contenido, no se muestra nada
        } else {


            this.vbox.setStyle("-fx-border-color: #eeeeee;-fx-border-radius: 5;-fx-border-insets: 5;-fx-border-width: 3;-fx-padding: 10 10 10 10;");//"-fx-border-style: dashed;");

            vbox.setAlignment(Pos.CENTER);
            this.vbox.setOnMouseClicked(event -> {
                //solo se deja borrar si no tiene productos
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
            if(item.getProductos().size()==0) {
                icon.setDescription("fas-trash");
            }
            if (item.getId() >= 0) {
                label.setText(item.getNombre()+"("+item.getProductos().size()+")");

            } else {
                label.setText("Nuevo");
            }
          //  this.icon.setDescription("fas-home");
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
                if(Files.exists(Path.of(item.getImg_src()))){
                    this.image.setImage(new Image("file:"+ item.getImg_src(),96,96,true,true));
                }
                else {
                    image.setImage(noImage);
                }
            }
            setGraphic(this.vbox); // Mostrar la imagen en la celda
        }
    }
}
