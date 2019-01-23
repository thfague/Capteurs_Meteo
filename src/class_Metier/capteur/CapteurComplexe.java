package class_Metier.capteur;

import java.util.*;

public class CapteurComplexe extends CapteurAbstrait implements Observer {

    private Map<CapteurAbstrait, Integer> listeCapteur;

    public CapteurComplexe(Map<CapteurAbstrait, Integer> m, String nom) {
        super(0f, nom);
        this.listeCapteur = m;
        this.setValeur(calculValeur());
    }

    public Map<CapteurAbstrait,Integer> getListeCapteur() { return listeCapteur; }

    //Méthode qui czlcul lé valeur du capteur ccmplexe en prenant en compte tous les capteurs liés et keur coefficient
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

    //Méthode qui change en recalculant la valeur du capteur complexe
    //Méthode appelé dès qu'un capteur lié change de valeur
    @Override
    public void update(Observable o, Object arg) {
        this.setValeur(calculValeur());
    }

    //Méthode qui ajoute un capteur et son coefficient à la Map de capteur lié
    public void ajoutCapteur(CapteurAbstrait c, Integer coeff) {
        this.listeCapteur.put(c, coeff);
        c.addObserver(this);
        this.setValeur(calculValeur());
    }
}
