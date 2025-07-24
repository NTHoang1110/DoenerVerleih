package de.hsrm.mi.web.projekt.entities.zutat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Zutat {

    @Id
    @Size(min = 13, max = 13)
    private String ean;

    @Version
    private long version;

    @NotBlank
    private String name;

    @Min(0)
    @Max(2)
    private int vegetarizitaet;

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

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

    @Override
    public String toString() {
        return "Zutat [ean=" + ean + ", version=" + version + ", name=" + name + ", vegetarizitaet=" + vegetarizitaet
                + "]";
    }

}
