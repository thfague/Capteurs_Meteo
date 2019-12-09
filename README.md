Application en JavaFX qui permet de monitorer des capteurs "météo".

Un capteur génère des valeurs (ici température).
Il est possible de visualiser ces capteurs sous 3 formes (fenêtre):
- Valeur digitale.
- Valeur sous forme d'image - de 0°C=glace 0°C->22°C=ciel dégagé et + de 22°C=soleil.
- Valeur sous forme d'un thermomètre (composant JavaFX personnalisé).

Une fenêtre n'affiche qu'un compteur et pour 1 capteur donné, toutes les fenêtres qui l'affichent sont synchronisées sur ses changement.

Un capteur génère une valeur toutes les X secondes (X est donné à la création et peut être modifié à tout moment).
Tous les capteurs effectuent leur tâche en parallèle.
Chaque capteur possède son algorithme de génération de valeur (qui pourra être modifié au cours du programme).

Il existe aussi des capteurs plus complexes dont la valeur dépend des autres capteurs auquels il est lié. 
Un capteur complexe C connaît différent capteurs(peu importe leur type)
On veut visualiser ces capteurs sous forme hiérarchique et pouvoir continuer à les configurer comme avant.
