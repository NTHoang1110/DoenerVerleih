package de.hsrm.mi.web.projekt.messaging;

public class FrontendNachrichtEvent {
    public enum EventTyp {
        DOENER
    }

    public enum Operation {
        CREATE, UPDATE, DELETE, BOOKED, RETURNED
    }

    private EventTyp typ;
    private long id;
    private Operation op;

    public FrontendNachrichtEvent(EventTyp typ, long id, Operation op) {
        this.typ = typ;
        this.id = id;
        this.op = op;
    }

    public EventTyp getTyp() {
        return typ;
    }

    public long getId() {
        return id;
    }

    public Operation getOp() {
        return op;
    }

    public void setTyp(EventTyp typ) {
        this.typ = typ;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOp(Operation op) {
        this.op = op;
    }
}
