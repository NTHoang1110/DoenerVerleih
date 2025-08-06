package de.hsrm.mi.web.projekt.doener.services;

import java.util.Collection;
import java.util.Optional;

import de.hsrm.mi.web.projekt.entities.doener.Doener;

public interface DoenerService {
    Doener saveDoener(Doener d);

    Optional<Doener> findDoenerById(long id);

    Collection<Doener> findAllDoener();

    void deleteDoenerById(long id);

}
