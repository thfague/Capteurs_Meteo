package Vue;

import java.util.Random;

public class GenerationValeur {
    private final float min = -273.15f;
    private Random r;

    public float valAleatoireInfini() {
        float max = Float.MAX_VALUE;
        r = new Random();
        return min + r.nextFloat() * (max - min);
    }

    public float valAleatoireBorne(int min, int max) {
        r = new Random();
        return min + r.nextFloat() * (max - min);
    }

    public float valAleatoireReelle(float valAvant, int diff) {
        r = new Random();
        return (valAvant - diff) + r.nextFloat() * ((valAvant + diff) - (valAvant - diff));
    }
}