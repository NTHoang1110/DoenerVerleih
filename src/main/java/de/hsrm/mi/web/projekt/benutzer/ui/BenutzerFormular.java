package de.hsrm.mi.web.projekt.benutzer.ui;

import de.hsrm.mi.web.projekt.validators.GeeigneteLosung;
import jakarta.validation.constraints.*;

public class BenutzerFormular {
    @NotBlank
    @Size(min = 3, max = 60)
    private String name;
    @Email
    private String email;
    private int vegetarizitaet;
    private String[] rolle = { "ADMIN", "KUNDE", "GESPERRT" };
    private String rolleAuswahl;
    @GeeigneteLosung()
    private String losung;
    private String losungWdh;

    public BenutzerFormular(String name, String email, int vegetarizitaet, String rolleAuswahl, String losung,
            String losungWdh) {
        this.name = name;
        this.email = email;
        this.vegetarizitaet = vegetarizitaet;
        this.rolleAuswahl = rolleAuswahl;
        this.losung = losung;
        this.losungWdh = losungWdh;
    }

    public BenutzerFormular() {
        this("", "", 0, "GESPERT", "", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVegetarizitaet() {
        return vegetarizitaet;
    }

    public void setVegetarizitaet(int vegetarizitaet) {
        this.vegetarizitaet = vegetarizitaet;
    }

    public String[] getRolle() {
        return rolle;
    }

    public void setRolle(String[] rolle) {
        this.rolle = rolle;
    }

    public String getRolleAuswahl() {
        return rolleAuswahl;
    }

    public void setRolleAuswahl(String rolleAuswahl) {
        this.rolleAuswahl = rolleAuswahl;
    }

    public String getLosung() {
        return losung;
    }

    public void setLosung(String losung) {
        this.losung = losung;
    }

    public String getLosungWdh() {
        return losungWdh;
    }

    public void setLosungWdh(String losungWdh) {
        this.losungWdh = losungWdh;
    }

    @Override
    public String toString() {
        return "BenutzerFormular [name=" + name + ", email=" + email + ", vegetarizitaet=" + vegetarizitaet
                + ", rolleAuswahl=" + rolleAuswahl + ", losung=" + losung + ", losungWdh=" + losungWdh + "]";
    }
}
