package de.hsrm.mi.web.projekt.entities.doener;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import de.hsrm.mi.web.projekt.entities.zutat.Zutat;

import java.util.List;

@Entity
public class Doener {
    @GeneratedValue
    @Id
    private long id;

    @Version
    private long version;

    @NotBlank
    @Size(min = 3, max = 40)
    private String bezeichnung;

    @Min(0)
    private int preis;

    @Min(0)
    @Max(2)
    private int vegetarizitaet;

    @Min(0)
    private int bestand;

    @ManyToMany
    @JoinTable(name = "doener_zutat", joinColumns = @JoinColumn(name = "doener_id"), inverseJoinColumns = @JoinColumn(name = "zutat_ean"))
    private List<Zutat> zutaten;

    private int entliehen;

    public int getEntliehen() {
        return entliehen;
    }

    public void setEntliehen(int entliehen) {
        this.entliehen = entliehen;
    }

    public int getVerfuegbar() {
        return bestand - entliehen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public int getVegetarizitaet() {
        return vegetarizitaet;
    }

    public void setVegetarizitaet(int vegetarizitaet) {
        this.vegetarizitaet = vegetarizitaet;
    }

    public int getBestand() {
        return bestand;
    }

    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    public List<Zutat> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<Zutat> zutaten) {
        this.zutaten = zutaten;
    }

    @Override
    public String toString() {
        return "Doener [id=" + id + ", version=" + version + ", bezeichnung=" + bezeichnung + ", preis=" + preis
                + ", vegetarizitaet=" + vegetarizitaet + ", bestand=" + bestand + ", zutaten=" + zutaten + "]";
    }
}
