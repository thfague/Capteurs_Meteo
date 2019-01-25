package class_Metier.generateur;

import java.util.Random;

public class GenerationAleatoireBorne implements IGenerationValeur {

    private int min;
    private int max;

    public GenerationAleatoireBorne(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public float generer() {
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }

}
