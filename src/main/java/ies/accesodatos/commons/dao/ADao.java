package ies.accesodatos.commons.dao;

public abstract class ADao<T,K> implements IDao<T,K> {
   // private DataBaseConnection connection;
    protected String tableName;
    public ADao() {
       // this.connection = null;
    }
   /* public ADao(DataBaseConnection connection) {
        this.connection = connection;
    }
    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }
    public DataBaseConnection getConnection() {
        return connection;
    }*/
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getTableName() {
        return tableName;
    }
}
