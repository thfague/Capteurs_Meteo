package Class_Metier;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static java.lang.Thread.sleep;

public class CapteurComplexe implements Runnable {

    private Map<Capteur, Integer> listeCapteur;
    private String nom;
    private float valeur = 0f;

    public float getValeur() { return valeur; }
    public void setValeur(float valeur) { this.valeur = valeur; }
    public Map<Capteur,Integer> getListeCapteur() { return listeCapteur; }

    public CapteurComplexe(Map<Capteur, Integer> m, String nom) {
        this.nom = nom;
        this.listeCapteur = m;
        calculValeur();
    }

    public void run() {
        try {
            while(true) {
                calculValeur();

                sleep(2000);
            }
        } catch (Exception e) {
            System.out.println("err");
        }
    }

    private void calculValeur() {
        float sommeCoeff = 0f;
        Set<Map.Entry<Capteur, Integer>> setlisteCapteur = listeCapteur.entrySet();
        Iterator<Map.Entry<Capteur, Integer>> it = setlisteCapteur.iterator();
        while(it.hasNext()){
            Map.Entry<Capteur, Integer> e = it.next();
            sommeCoeff = sommeCoeff + e.getValue();
            this.valeur = this.valeur + e.getKey().getValeur() * e.getValue();
        }
        this.valeur = this.valeur/sommeCoeff;
        //System.out.println(this.valeur);
    }
}
