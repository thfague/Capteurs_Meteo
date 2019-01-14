package class_Metier.capteur;

import class_Metier.generateur.GenerationValeurAbstrait;
import static java.lang.Thread.sleep;

public class Capteur extends CapteurAbstrait implements Runnable {

    private GenerationValeurAbstrait g;
    private Integer tpsChangement;
    private Thread t;

    public Capteur(float valeur, String nom, Integer tps, GenerationValeurAbstrait g) {
        super(valeur, nom);
        tpsChangement = tps;
        this.g = g;
        t = new Thread(this);
        t.start();
    }

    public GenerationValeurAbstrait getGenerationValeur() {
        return g;
    }

    public Integer getTpsChangement() {
        return tpsChangement;
    }

    public void run() {
        try {
            while(true) {
                this.setValeur(g.generer());
                sleep(tpsChangement);
            }
        } catch (Exception e) {
            System.out.println("err");
        }
    }
}
