package de.hsrm.mi.web.projekt.zutat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.zutat.Zutat;
import de.hsrm.mi.web.projekt.entities.zutat.ZutatRepository;

@Service
public class ZutatenServiceImpl implements ZutatenService {

    private final ZutatRepository zutatRepo;

    public ZutatenServiceImpl(ZutatRepository zutatRepo) {
        this.zutatRepo = zutatRepo;
    }

    @Override
    public List<Zutat> findAllZutaten() {
        return zutatRepo.findAll();
    }

    @Override
    public List<Zutat> findAllZutatenByEan(List<String> eans) {
        return zutatRepo.findAllById(eans);
    }
}
