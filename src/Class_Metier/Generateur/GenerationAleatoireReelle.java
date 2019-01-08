package Class_Metier.Generateur;

import java.util.Random;

public class GenerationAleatoireReelle implements GenerationValeurAbstrait {

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
