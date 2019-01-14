package class_Metier.capteur;

import java.util.*;

public class CapteurComplexe extends CapteurAbstrait implements Observer {

    private Map<CapteurAbstrait, Integer> listeCapteur;
    public Map<CapteurAbstrait,Integer> getListeCapteur() { return listeCapteur; }

    public CapteurComplexe(Map<CapteurAbstrait, Integer> m, String nom) {
        super(0f, nom);
        this.listeCapteur = m;
        this.setValeur(calculValeur());
    }

    private float calculValeur() {
        float sommeCoeff = 0f;
        float valeur = 0f;
        if(listeCapteur.size() == 0) {
            return valeur;
        }
        else {
            Set<Map.Entry<CapteurAbstrait, Integer>> setListeCapteur = this.listeCapteur.entrySet();
            Iterator<Map.Entry<CapteurAbstrait, Integer>> it = setListeCapteur.iterator();
            for (Map.Entry<CapteurAbstrait, Integer> m: setListeCapteur) {
                sommeCoeff = sommeCoeff + m.getValue();
                valeur = valeur + m.getKey().getValeur() * m.getValue();
            }
            return valeur / sommeCoeff;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setValeur(calculValeur());
    }

    public void ajoutCapteur(CapteurAbstrait c, Integer coeff) {
        this.listeCapteur.put(c, coeff);
        c.addObserver(this);
        this.setValeur(calculValeur());
    }
}
