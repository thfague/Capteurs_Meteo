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
    -aléatoire [-273,15;+inf] (zero absolu -> infini)
    -aléatoire borné [10;30]
    -à fenêtre glissante [-2;+2]



Ce que le prof m'a dis pour l'affichage/personnalisation d'une ListView:
	-Ne pas faire l'affichage avec le toString ! (-> toString plus pour debugger)
	-Chaque ligne de la ListView est une ListCell.
	-Elle est personnalisable grâce a: nomlisteview.setCellFactory(...)
	-La personnalisation ce fait avec updateItem(...) de la ListCell.
(Voir javadoc)
