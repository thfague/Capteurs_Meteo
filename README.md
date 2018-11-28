CONTRAINTES :

Semaine 1:
Je veux une application JavaFX qui permet de monitorer des capteurs
Un capteur génère des valeurs (ici température)
Je veux pouvoir visualiser ces capteurs sous 3 formes (fenêtre) :
    - sa valeur digitale
    - sa valeur sous forme d'image - 0 glace 0,22 ciel dégagé +22 soleil
    - sa valeur sous forme d'un thermomètre (composant JavaFX personnalisé)
Une fenêtre n'affiche qu'un compteur
Pour 1 capteur donné, toutes les fenêtres qui l'affichent sont synchronisées sur ses changement

Semaine 2:
*Un capteur génère une valeur toutes les X secondes (X sera donné à la création et pourra être modifié à tout moment).
*Tous les capteurs effectuent leur tâche en parallèle.
*Chaque capteur possède son algorithme de génération de valeur (qui pourra être modifié au cours du programme).
*3 algos =/= pour l'instant
    -aléatoire
    -aléatoire borné
    -à fenêtre glissante