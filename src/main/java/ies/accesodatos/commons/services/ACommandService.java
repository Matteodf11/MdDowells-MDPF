package ies.accesodatos.commons.services;

import com.google.common.eventbus.EventBus;

public abstract class ACommandService {

    private EventBus eventbus;
    public ACommandService(){
        eventbus = new EventBus();
    }
    public void setEventBus(EventBus eventbus) {
        this.eventbus = eventbus;
    }
    public EventBus getEventBus() {
        return eventbus;
    }
    public void register(Object object) {
        eventbus.register(object);
    }
    public void unregister(Object object) {
        eventbus.unregister(object);
    }
    public void post(String message,Event.ACTION action,Object item) {
        if(this.eventbus!=null){
            this.eventbus.post(new Event(message,action,item));
        }

    }

}
