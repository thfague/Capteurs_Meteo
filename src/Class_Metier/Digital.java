package Class_Metier;

public class Digital extends Capteur {

    public Digital(float valeur, String nom) {
        super(valeur, nom);
    }

    void notifier(float val) {
        setChanged();
        notifyObservers(val);
    }

    public void setChanged() {

    }

    public void notifyObservers(float val) {

    }


}
