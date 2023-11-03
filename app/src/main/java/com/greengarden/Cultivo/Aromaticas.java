package com.greengarden.Cultivo;

import com.greengarden.Cultivo.Consumos.Abono;
import com.greengarden.Cultivo.Consumos.Agua;

public class Aromaticas extends Abono {
    private Agua agua;

    public Aromaticas(double peso) {
        super(peso);
    }
    public Agua Hortalizas() {
        agua = new Agua(12.0);

        return null;
    }
}
