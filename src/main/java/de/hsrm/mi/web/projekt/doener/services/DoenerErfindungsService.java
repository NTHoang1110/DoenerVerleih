package de.hsrm.mi.web.projekt.doener.services;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.doener.Doener;
import de.hsrm.mi.web.projekt.entities.doener.DoenerRepository;
import de.hsrm.mi.web.projekt.entities.zutat.Zutat;

@Service
public class DoenerErfindungsService {

    @Autowired
    private DoenerRepository repository;
    private Random random = new Random();
    private DoenerNamingService namingService;

    public DoenerErfindungsService(DoenerNamingService namingService) {
        this.namingService = namingService;
    }

    public Doener generateRandomDoener(List<Zutat> alleZutaten) {
        Doener doener = new Doener();
        int maxCount = Math.min(5, alleZutaten.size() / 4);
        maxCount = Math.max(1, maxCount);
        int anzahlZutaten = random.nextInt(1, maxCount + 1);
        Collections.shuffle(alleZutaten);
        doener.setZutaten(alleZutaten.subList(0, anzahlZutaten));

        int minVeg = doener.getZutaten().stream().mapToInt(Zutat::getVegetarizitaet).min().orElse(0);
        doener.setVegetarizitaet(minVeg);

        doener.setPreis(random.nextInt(5, 30));

        doener.setBestand(random.nextInt(1, 101));

        int id = random.nextInt(1, 1001);
        doener.setBezeichnung(namingService.getName(id));
        return repository.save(doener);
    }
}
