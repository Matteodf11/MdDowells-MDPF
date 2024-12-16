package ies.accesodatos.ui.commons;

import java.util.function.BiConsumer;

public interface IWindows   {
    @FunctionalInterface
    public interface Action {
        void execute();
    }


    public void refresh();
    public void reload();
    public void setOnAction(BiConsumer<String,Object> onaction);

}
