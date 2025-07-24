package de.hsrm.mi.web.projekt.zutat.services;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.web.projekt.entities.zutat.Zutat;

public interface ZutatenService {
    List<Zutat> findAllZutaten();

    List<Zutat> findAllZutatenByEan(List<String> eans);
}
