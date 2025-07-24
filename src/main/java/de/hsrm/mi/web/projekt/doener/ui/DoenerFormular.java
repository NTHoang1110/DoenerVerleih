package de.hsrm.mi.web.projekt.doener.ui;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DoenerFormular {

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

    private List<String> ausgewaehlteZutatenEans;

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

    public List<String> getAusgewaehlteZutatenEans() {
        return ausgewaehlteZutatenEans;
    }

    public void setAusgewaehlteZutatenEans(List<String> ausgewaehlteZutatenEans) {
        this.ausgewaehlteZutatenEans = ausgewaehlteZutatenEans;
    }

    @Override
    public String toString() {
        return "DoenerFormular [bezeichnung=" + bezeichnung + ", preis=" + preis + ", vegetarizitaet=" + vegetarizitaet
                + ", bestand=" + bestand + "]";
    }

}
