package class_Metier.generateur;

import java.util.Random;

public class GenerationAleatoireReelle implements IGenerationValeur {

    private float valAvant;
    private int diff;

    public GenerationAleatoireReelle(float valAvant, int diff) {
        this.valAvant = valAvant;
        this.diff = diff;
    }

    public float generer() {
        Random r = new Random();
        return (valAvant - diff) + r.nextFloat() * ((valAvant + diff) - (valAvant - diff));
    }
}
