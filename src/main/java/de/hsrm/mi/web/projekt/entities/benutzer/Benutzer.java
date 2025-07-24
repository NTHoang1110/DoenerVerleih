package de.hsrm.mi.web.projekt.entities.benutzer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.util.List;

import de.hsrm.mi.web.projekt.entities.doener.Doener;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;

@Entity
public class Benutzer {
    @Version
    private long version;

    @Id
    private String loginName;

    @NotBlank
    @Size(min = 3, max = 60)
    private String name;

    @NotBlank
    @Email
    private String email;

    @Min(0)
    @Max(2)
    private int vegetarizitaet;

    @NotBlank
    private String rolleAuswahl;

    @NotBlank
    private String losung;

    @ManyToMany
    private List<Doener> entlieheneDoener;

    public List<Doener> getEntlieheneDoener() {
        return entlieheneDoener;
    }

    public void setEntlieheneDoener(List<Doener> entlieheneDoener) {
        this.entlieheneDoener = entlieheneDoener;
    }

    public Benutzer(String name, String email, int vegetarizitaet, String rolleAuswahl, String losung) {
        this.name = name;
        this.email = email;
        this.vegetarizitaet = vegetarizitaet;
        this.rolleAuswahl = rolleAuswahl;
        this.losung = losung;
    }

    public Benutzer() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Benutzer [loginName=" + loginName + ", name=" + name + ", email=" + email + ", vegetarizitaet="
                + vegetarizitaet + ", rolleAuswahl=" + rolleAuswahl + ", losung=" + losung + "]";
    }

}
