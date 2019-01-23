package class_Metier.generateur;

import java.util.Random;

public class GenerationAleatoireInfini implements GenerationValeurAbstrait {

    private final float minPossible = -273.15f;

    public float generer() {
        float max = Float.MAX_VALUE;
        Random r = new Random();
        return minPossible + r.nextFloat() * (max - minPossible);
    }
}
