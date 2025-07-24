package de.hsrm.mi.web.projekt.entleihung.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.benutzer.services.BenutzerService;
import de.hsrm.mi.web.projekt.doener.services.DoenerService;
import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.doener.Doener;

@Service
public class EntleihungService {
    @Autowired
    DoenerService dService;
    @Autowired
    BenutzerService bService;

    public void entleiheDoener(long doenerId, String loginName) {
        try {
            Doener dToAdd = dService.findDoenerById(doenerId).get();
            Benutzer bToEdit = bService.findBenutzerById(loginName).get();
            if (dToAdd.getVerfuegbar() == 0) {
                throw new EntleihException();
            }
            bToEdit.getEntlieheneDoener().add(dToAdd);
            dToAdd.setEntliehen(dToAdd.getEntliehen() + 1);
            bService.saveBenutzer(bToEdit);
            dService.saveDoener(dToAdd);
        } catch (EntleihException | NullPointerException e) {
            System.out.println("HAHAHAHAHHAHAHAHAHAHHAHAHAHAHHA");
        }
    }

    public void zurueckgebeDoener(long doenerId, String loginName) {
        try {
            Doener dToAdd = dService.findDoenerById(doenerId).get();
            Benutzer bToEdit = bService.findBenutzerById(loginName).get();
            bToEdit.getEntlieheneDoener().remove(dToAdd);
            dToAdd.setEntliehen(dToAdd.getEntliehen() - 1);
            bService.saveBenutzer(bToEdit);
            dService.saveDoener(dToAdd);
        } catch (EntleihException | NullPointerException e) {
            System.out.println("HAHAHAHAHHAHAHAHAHAHHAHAHAHAHHA");
        }
    }

    public List<Doener> findeEntleihungenVonBenutzer(String loginName) {
        try {
            Benutzer bOptional = bService.findBenutzerById(loginName).get();
            return bOptional.getEntlieheneDoener();
        } catch (EntleihException e) {
            return null;
        }
    }
}