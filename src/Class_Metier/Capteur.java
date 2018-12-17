package Class_Metier;

import Vue.GenerationValeur;
import javafx.beans.Observable;

import static java.lang.Thread.sleep;

public class Capteur extends CapteurAbstrait implements Runnable {

    GenerationValeur g = new GenerationValeur();
    Integer tpsChangement;

    public Capteur(float valeur, String nom, Integer tps) {
        super(valeur, nom);
        tpsChangement = tps;
        Thread fred = new Thread(this);
        fred.start();

    }

    public void run() {
        try {
            while(true) {
                this.setValeur(g.valAleatoireBorne(-10,30));
                //this.setValeur(g.valAleatoireReelle(this.getValeur(),2));
                //this.setValeur(g.valAleatoireInfini());

                sleep(tpsChangement);
            }
        } catch (Exception e) {
            System.out.println("err");
        }
    }
}
