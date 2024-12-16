package ies.accesodatos.ui.commons;

public interface IViewModel {
    /**
     * actualiza los listados
     */
    public void refresh();

    /**
     * carga los datos desde los servicios
     */
    public void reload();

}
