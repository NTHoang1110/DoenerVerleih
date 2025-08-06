package de.hsrm.mi.web.projekt.zutat.ui;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ZutatFormular {
    @NotBlank
    private String name;

    @Size(min = 13, max = 13)
    private String ean;

    @Min(0)
    @Max(2)
    private int vegetarizitaet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVegetarizitaet() {
        return vegetarizitaet;
    }

    public void setVegetarizitaet(int vegetarizitaet) {
        this.vegetarizitaet = vegetarizitaet;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

}
