package class_Metier.capteur;

import class_Metier.generateur.IGenerationValeur;

import static java.lang.Thread.sleep;

public class Capteur extends CapteurAbstrait implements Runnable {

    private IGenerationValeur g;
    private Integer tpsChangement;

    public Capteur(float valeur, String nom, Integer tps, IGenerationValeur g) {
        super(valeur, nom);
        tpsChangement = tps;
        this.g = g;
        Thread t = new Thread(this);
        t.start();
    }

    public void setG(IGenerationValeur g) {
        this.g = g;
    }

    public void setTpsChangement(Integer i) {
        tpsChangement = i;
    }

    public Integer getTpsChangement() {
        return tpsChangement;
    }

    //Méthode qui génère la nouvelle valeur du capteur selon sa générationValeur toutes les X secondes
    public void run() {
        try {
            while (true) {
                this.setValeur(g.generer());
                sleep(tpsChangement);
            }
        } catch (Exception e) {
            System.out.println("err");
        }
    }
}
