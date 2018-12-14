package Class_Metier;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class CapteurAbstrait {

    private FloatProperty valeur = new SimpleFloatProperty();
    public float getValeur() { return this.valeur.get(); }
    public void setValeur(float valeur) { this.valeur.set(valeur); }
    public FloatProperty valeurProperty() { return valeur; }

    private StringProperty nom = new SimpleStringProperty();
    public String getNom() { return this.nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public CapteurAbstrait(float valeur, String nom) {
        this.nom.setValue(nom);
        this.valeur.setValue(valeur);
    }

}
