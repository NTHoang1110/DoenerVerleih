package de.hsrm.mi.web.projekt.benutzer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import de.hsrm.mi.web.projekt.benutzer.mapper.BenutzerMapper;
import de.hsrm.mi.web.projekt.benutzer.services.BenutzerService;
import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes("version")
public class BenutzerController {
    private static final Logger logger = LoggerFactory.getLogger(BenutzerController.class);
    private BenutzerMapper mapper;
    private BenutzerService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public BenutzerController(BenutzerMapper mapper, BenutzerService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/benutzer")
    public String benutzerListe(Model model) {
        var benutzerliste = service.findAllBenutzer();
        model.addAttribute("benutzerliste", benutzerliste);
        return "benutzer/liste";
    }

    @GetMapping("/benutzer/{loginName}/delete")
    public String deleteBenutzer(@PathVariable("loginName") String loginName) {
        service.deleteBenutzerById(loginName);
        return "redirect:/benutzer";
    }

    @GetMapping("/benutzer/{nameInPath}")
    public String getBenutzer(@PathVariable("nameInPath") String name, Model model) {
        logger.info("GET Benutzer: {}", name);

        BenutzerFormular formular;
        Optional<Benutzer> optBenutzer = service.findBenutzerById(name);

        if (optBenutzer.isPresent()) {
            Benutzer benutzer = optBenutzer.get();
            long version = benutzer.getVersion();
            formular = mapper.benutzerToBenutzerFormular(benutzer);
            model.addAttribute("version", version);

        } else {
            model.addAttribute("version", 0);
            formular = new BenutzerFormular();
        }

        model.addAttribute("nameInPath", name);
        model.addAttribute("formular", formular);
        return "benutzer/bearbeiten";
    }

    @PostMapping("/benutzer/{nameInPath}")
    public String benutzer(
            @Valid @ModelAttribute("formular") BenutzerFormular formular,
            BindingResult formularResult,
            @PathVariable("nameInPath") String name,
            Model model,
            @ModelAttribute("version") long version) {
        model.addAttribute("nameInPath", name);
        if (formular.getLosung() != null && !formular.getLosung().isEmpty()) {
            if (!formular.getLosung().equals(formular.getLosungWdh())) {
                formularResult.rejectValue("losungWdh", "benutzer.fehler.losungwiederholung");
                return "benutzer/bearbeiten";
            }
        }
        if (formularResult.hasErrors()) {
            logger.info("Eingabe nicht gültig!");
            return "benutzer/bearbeiten";
        }

        logger.info("POST Benutzer {}, Info: {}", name, formular);
        try {
            Benutzer benutzer = mapper.benutzerFormularToBenutzer(formular);
            benutzer.setVersion(version);
            benutzer.setLoginName(name);

            if (formular.getLosung().isBlank()) {
                Optional<Benutzer> optAlt = service.findBenutzerById(name);
                if (optAlt.isPresent()) {
                    benutzer.setLosung(optAlt.get().getLosung());
                } else {
                    throw new BenutzerException("Schade, es gab einen Fehler beim Abspeichern");
                }
            }
            benutzer.setLosung(passwordEncoder.encode(benutzer.getLosung()));
            service.saveBenutzer(benutzer);
            Benutzer benutzer2 = service.saveBenutzer(benutzer);
            long version2 = benutzer2.getVersion();
            model.addAttribute("version", version2);
            logger.info("Benutzer {} gespeichert.", name);

        } catch (BenutzerException | ObjectOptimisticLockingFailureException ex) {
            model.addAttribute("info", ex.getMessage());
            logger.warn("Benutzerfehler: {}", ex.getMessage());
            return "benutzer/bearbeiten";
        }
        return "benutzer/bearbeiten";
    }

    @GetMapping("/benutzer/{loginName}/hx/feld/{feldname}")
    public String getFeld(@PathVariable("loginName") String loginName,
            @PathVariable("feldname") String feldname,
            Model model) {
        var benutzer = service.findBenutzerById(loginName).orElseThrow();
        String wert = feldname.equals("name") ? benutzer.getName() : benutzer.getEmail();

        model.addAttribute("loginName", loginName);
        model.addAttribute("feldname", feldname);
        model.addAttribute("wert", wert);

        return "benutzer/eingabefeld :: bearbeiten";
    }

    @PutMapping("/benutzer/{loginName}/hx/feld/{feldname}")
    public String updateFeld(@PathVariable("loginName") String loginName,
            @PathVariable("feldname") String feldname,
            @RequestParam("wert") String neuerWert,
            Model model) {
        try {
            var benutzer = service.aktualisiereBenutzerAttribut(loginName, feldname, neuerWert);
            model.addAttribute("loginName", loginName);
            model.addAttribute("feldname", feldname);
            model.addAttribute("wert", feldname.equals("name") ? benutzer.getName() : benutzer.getEmail());

            return "benutzer/eingabefeld :: ausgeben";
        } catch (TransactionSystemException ex) {
            String userMessage = "";
            Throwable cause = ex.getRootCause();

            if (cause instanceof ConstraintViolationException cve) {
                userMessage += cve.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("; "));
            } else {
                userMessage += "Unbekannter Fehler";
            }
            var fallback = service.findBenutzerById(loginName).orElseThrow();
            model.addAttribute("loginName", loginName);
            model.addAttribute("feldname", feldname);
            model.addAttribute("wert", feldname.equals("name") ? fallback.getName() : fallback.getEmail());
            model.addAttribute("fehler", userMessage);

            return "benutzer/eingabefeld :: bearbeiten";
        }
    }

}
