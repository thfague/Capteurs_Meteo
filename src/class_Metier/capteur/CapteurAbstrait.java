package class_Metier.capteur;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Observable;

public abstract class  CapteurAbstrait extends Observable {

    private FloatProperty valeur = new SimpleFloatProperty();
    float getValeur() { return this.valeur.get(); }
    void setValeur(float valeur) { this.valeur.set(valeur); setChanged(); notifyObservers(); }
    public FloatProperty valeurProperty() { return this.valeur; }

    private StringProperty nom = new SimpleStringProperty();
    public String getNom() { return this.nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public CapteurAbstrait(float valeur, String nom) {
        this.nom.setValue(nom);
        this.valeur.setValue(valeur);
    }
}
