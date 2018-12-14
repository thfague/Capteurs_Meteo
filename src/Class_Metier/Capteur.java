package Class_Metier;

import Vue.GenerationValeur;

import static java.lang.Thread.sleep;

public class Capteur extends CapteurAbstrait implements Runnable{

    GenerationValeur g = new GenerationValeur();

    public Capteur(float valeur, String nom) {
        super(valeur, nom);
    }

    public void run() {
        try {
            while(true) {
                this.setValeur(g.valAleatoireBorne(-10,30));
                //this.setValeur(g.valAleatoireReelle(this.getValeur(),2));
                //this.setValeur(g.valAleatoireInfini());

                sleep(500);
            }
        } catch (Exception e) {
            System.out.println("err");
        }
    }
}
