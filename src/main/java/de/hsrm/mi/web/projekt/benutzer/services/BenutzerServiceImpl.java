package de.hsrm.mi.web.projekt.benutzer.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;
import jakarta.transaction.Transactional;

@Service
public class BenutzerServiceImpl implements BenutzerService {
    @Autowired
    BenutzerRepository benutzerRepository;

    @Override
    public Benutzer saveBenutzer(Benutzer b) {
        return benutzerRepository.save(b);
    }

    @Override
    @Transactional
    public Optional<Benutzer> findBenutzerById(String loginName) {
        return benutzerRepository.findById(loginName);
    }

    @Override
    public Collection<Benutzer> findAllBenutzer() {
        return benutzerRepository.findAll();
    }

    @Override
    public void deleteBenutzerById(String loginName) {
        benutzerRepository.deleteById(loginName);
    }

    @Transactional
    public Benutzer aktualisiereBenutzerAttribut(String loginName, String feldname, String wert) {
        var benutzer = benutzerRepository.findById(loginName).orElseThrow();

        switch (feldname) {
            case "name" -> benutzer.setName(wert);
            case "email" -> benutzer.setEmail(wert);
            default -> throw new IllegalArgumentException("Unbekanntes Feld: " + feldname);
        }

        return benutzerRepository.save(benutzer);
    }

}
