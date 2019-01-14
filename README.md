CONTRAINTES :

Semaine 1:
**Je veux une application JavaFX qui permet de monitorer des capteurs
**Un capteur génère des valeurs (ici température)
**Je veux pouvoir visualiser ces capteurs sous 3 formes (fenêtre) :
**   - sa valeur digitale
**   - sa valeur sous forme d'image - 0 glace 0,22 ciel dégagé +22 soleil
**    - sa valeur sous forme d'un thermomètre (composant JavaFX personnalisé)
**Une fenêtre n'affiche qu'un compteur
**Pour 1 capteur donné, toutes les fenêtres qui l'affichent sont synchronisées sur ses changement

Semaine 2:
**Un capteur génère une valeur toutes les X secondes (X sera donné à la création et pourra être modifié à tout moment).
**Tous les capteurs effectuent leur tâche en parallèle.
**Chaque capteur possède son algorithme de génération de valeur (qui pourra être modifié au cours du programme).
**3 algos =/= pour l'instant
**    -aléatoire [-273,15;+inf] (zero absolu -> infini)
**    -aléatoire borné [10;30]
**    -à fenêtre glissante [-2;+2]

Semaine 3:
**On va à présent visualiser des 'capteurs' plus complexes dont la valeur dépend d'autres capteurs
**On pourra appeler CapteurComplexe
**Un capteur complexe C connaît différent capteurs(peu importe leur type)
**c1,...,cN et génère une nouvelle valeur dès que l'un d'eux change tel que
**    Cc.valeur = (somme'i=1 à N' ai*ci.valeur)/(somme'i=1 a N' ai )
**    où les ai sont les pondérations de chaque capteur
**On veut visualiser ces capteurs sous forme hiérarchique et pouvoir continuer à les configurer comme avant.
**Pour les capteurs complexes cela voudra dire pouvoir ajouter un s/s capteur et pouvoir changer son coefficient à associer

A FAIRE :
    un capteur peut modifier son nom
    ajouter un capteur utilise AffichageConfigCapt

POSSIBLE AMELIORATION :
    * Verification : ne pas pour mettre de string dans minimun et max dans ajouter un capteur
    * Stylisé l'application
    * tous capteur peut modifié son nom
    *** on peut ajouter un capteur inexistant à un capteur complexe --> FAIT MAIS PB : liste vide ajout capteur complexe dans ajout capteur inexistant