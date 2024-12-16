package ies.accesodatos.commons.services;

public class Event {
    public enum ACTION{
        ADD,
        DELETE,
        UPDATE
    }
    private String message;
    private ACTION action;
    private Object data;
    public Event(String message, ACTION action, Object data) {
        this.message = message;
        this.action = action;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ACTION getAction() {
        return action;
    }

    public void setAction(ACTION action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "message='" + message + '\'' +
                ", action=" + action +
                ", data=" + data +
                '}';
    }
}
