package Class_Metier.Generateur;

import java.util.Random;

public class GenerationAleatoireInfini implements GenerationValeurAbstrait {

    private final float min = -273.15f;

    public float generer() {
        float max = Float.MAX_VALUE;
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }
}
