package class_Metier.generateur;

import java.util.Random;

public class GenerationAleatoireInfini implements IGenerationValeur {

    private static final float MIN_POSSIBLE = -273.15f;

    public float generer() {
        float max = Float.MAX_VALUE;
        Random r = new Random();
        return MIN_POSSIBLE + r.nextFloat() * (max - MIN_POSSIBLE);
    }
}
