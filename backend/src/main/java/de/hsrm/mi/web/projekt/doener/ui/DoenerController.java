package de.hsrm.mi.web.projekt.doener.ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import de.hsrm.mi.web.projekt.doener.mapper.DoenerMapper;
import de.hsrm.mi.web.projekt.doener.services.DoenerErfindungsService;
import de.hsrm.mi.web.projekt.doener.services.DoenerException;
import de.hsrm.mi.web.projekt.doener.services.DoenerService;
import de.hsrm.mi.web.projekt.entities.doener.Doener;
import de.hsrm.mi.web.projekt.entities.zutat.Zutat;
import de.hsrm.mi.web.projekt.zutat.services.ZutatenService;
import de.hsrm.mi.web.projekt.zutat.ui.ZutatFormular;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.slf4j.Logger;

@Controller
@SessionAttributes("version")
public class DoenerController {
    private static final Logger logger = LoggerFactory.getLogger(DoenerController.class);
    private DoenerMapper mapper;
    private DoenerService service;
    private ZutatenService zutatenService;
    private DoenerErfindungsService erfindungsService;

    public DoenerController(DoenerMapper mapper, DoenerService service, ZutatenService zutatenService,
            DoenerErfindungsService erfindungsService) {
        this.mapper = mapper;
        this.service = service;
        this.zutatenService = zutatenService;
        this.erfindungsService = erfindungsService;
    }

    @GetMapping("/doener")
    public String doenerListe(Model model) {
        var doenerliste = service.findAllDoener();
        model.addAttribute("doenerliste", doenerliste);
        return "doener/liste";
    }

    @GetMapping("/doener/{id}")
    public String getDoener(@PathVariable("id") long id, Model model) {

        DoenerFormular formular;
        Optional<Doener> optDoener = service.findDoenerById(id);

        if (optDoener.isPresent()) {
            Doener doener = optDoener.get();
            long version = doener.getVersion();
            formular = mapper.doenerToDoenerFormular(doener);
            formular.setAusgewaehlteZutatenEans(
                    doener.getZutaten().stream()
                            .map(Zutat::getEan)
                            .collect(Collectors.toList()));
            model.addAttribute("version", version);

        } else {
            model.addAttribute("version", 0);
            formular = new DoenerFormular();
        }
        List<ZutatFormular> alleZutaten = mapper.zutatListToZutatFormList(zutatenService.findAllZutaten());
        model.addAttribute("alleZutaten", alleZutaten);
        model.addAttribute("id", id);
        model.addAttribute("formular", formular);
        return "doener/bearbeiten";
    }

    @PostMapping("/doener/{id}")
    public String doener(
            @Valid @ModelAttribute("formular") DoenerFormular formular,
            BindingResult formularResult,
            @PathVariable("id") long id,
            Model model,
            @ModelAttribute("version") long version) {
        if (formularResult.hasErrors()) {
            return "doener/bearbeiten";
        }
        try {
            List<String> ausgewaehlteEans = formular.getAusgewaehlteZutatenEans();
            List<Zutat> zutaten = zutatenService.findAllZutatenByEan(ausgewaehlteEans);

            Doener doener = mapper.doenerFormularToDoener(formular);
            doener.setId(id);
            doener.setVersion(version);
            if (!zutaten.isEmpty()) {
                int min = zutaten.stream().mapToInt(Zutat::getVegetarizitaet).min().orElse(0);
                doener.setVegetarizitaet(min);
                doener.setZutaten(zutaten);
            } else {
                doener.setVegetarizitaet(formular.getVegetarizitaet());
            }
            Doener doener2 = service.saveDoener(doener);
            long version2 = doener2.getVersion();
            model.addAttribute("version", version2);

            return "redirect:/doener";
        } catch (DoenerException | ObjectOptimisticLockingFailureException ex) {
            model.addAttribute("info", ex.getMessage());
            model.addAttribute("id", id);
            return "doener/bearbeiten";
        }
    }

    @GetMapping("/doener/erfinden")
    public String postMethodName() {
        Doener doener = erfindungsService.generateRandomDoener(zutatenService.findAllZutaten());
        service.saveDoener(doener);
        return "redirect:/doener";
    }

    @GetMapping("/doener/{id}/delete")
    public String deleteBenutzer(@PathVariable("id") long id) {
        service.deleteDoenerById(id);
        return "redirect:/doener";
    }

}
