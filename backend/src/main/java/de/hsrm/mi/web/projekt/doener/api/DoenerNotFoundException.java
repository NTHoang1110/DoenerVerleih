package de.hsrm.mi.web.projekt.doener.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoenerNotFoundException extends RuntimeException {
    public DoenerNotFoundException(long id) {
        super("Döner " + id + " nicht gefunden.");
    }
}
