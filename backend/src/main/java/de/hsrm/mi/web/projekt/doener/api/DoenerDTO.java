package de.hsrm.mi.web.projekt.doener.api;

import java.util.List;

public record DoenerDTO(
        long id,
        String bezeichnung,
        int preis,
        int vegetarizitaet,
        int verfuegbar,
        List<ZutatDTO> zutaten) {
}
