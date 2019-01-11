package Class_Metier.Capteur;

import Class_Metier.Generateur.GenerationValeurAbstrait;
import static java.lang.Thread.sleep;

public class Capteur extends CapteurAbstrait implements Runnable {

    GenerationValeurAbstrait g;
    Integer tpsChangement;
    Thread t;

    public Capteur(float valeur, String nom, Integer tps, GenerationValeurAbstrait g) {
        super(valeur, nom);
        tpsChangement = tps;
        this.g = g;
        t = new Thread(this);
        t.start();
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

    public void stop() {
        t.interrupt();
    }
}
