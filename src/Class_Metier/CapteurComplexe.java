package Class_Metier;

import java.util.List;

import static java.lang.Thread.sleep;

public class CapteurComplexe implements Runnable{
    private List<Capteur> listeCapteur;
    private String nom;

    private float valeur = 0f;
    public float getValeur() { return valeur; }
    public void setValeur(float valeur) { this.valeur = valeur; }

    public CapteurComplexe(List<Capteur> listeCapteur, String nom) {
        float sommeCoeff = 0f;
        this.nom = nom;
        this.listeCapteur = listeCapteur;
        for (Capteur capteur : listeCapteur) {
            sommeCoeff = sommeCoeff + capteur.getCoeff();
            this.valeur = this.valeur + capteur.getValeur() * capteur.getCoeff();
        }
        this.valeur = this.valeur/sommeCoeff;
    }

    public CapteurComplexe ajoutCapteur(CapteurComplexe capteurComplexe,Capteur capteur) {
        capteurComplexe.listeCapteur.add(capteur);
        return capteurComplexe;
    }

    public void run() {
        try {
            while(true) {
                float sommeCoeff = 0f;
                for(Capteur capteur : listeCapteur) {
                    sommeCoeff = sommeCoeff + capteur.getCoeff();
                    this.valeur = this.valeur + capteur.getValeur() * capteur.getCoeff();
                }
                this.valeur = this.valeur/sommeCoeff;

                sleep(500);
            }
        } catch (Exception e) {
            System.out.println("err");
        }
    }
}
