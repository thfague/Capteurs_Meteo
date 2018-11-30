package Class_Metier;

public abstract class Capteur {
    private float valeur;
    private String nom;

    public Capteur(float valeur, String nom) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public float getValeur() {
        return valeur;
    }

    public String getNom() { return nom; }
}
