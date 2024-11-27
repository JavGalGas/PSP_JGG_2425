package org.example.versionA;

public class Capsula {
    private String nombreVariedad;
    private int intensidad;

    public Capsula(String nombreVariedad, int intensidad) {
        this.nombreVariedad = nombreVariedad;
        this.intensidad = intensidad<1 || intensidad>9 ? 1 : intensidad;
    }

    public String getNombreVariedad() {
        return nombreVariedad;
    }

    public int getIntensidad() {
        return intensidad;
    }

}
