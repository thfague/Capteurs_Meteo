package class_Metier.generateur;

import java.util.Random;

public class GenerationAleatoireInfini implements GenerationValeurAbstrait {

    public float generer() {
        float max = Float.MAX_VALUE;
        Random r = new Random();
        return -273.15f + r.nextFloat() * (max + 273.15f);
    }
}
