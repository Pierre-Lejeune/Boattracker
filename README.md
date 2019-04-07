# Boat Tracker

Voici le rendu de notre projet d'application mobiles 'Boat-Tracker'

## Informations
L’apk et à installer sur le téléphone et les fichiers de l’application ce trouve dans ‘AppFile‘
Les fonctionnalités que nous avons réalisées peuvent se voir dans les issues ‘close’, nous n’avons donc pas instaurer la connexion google mais l’utilisateur peut se créer un compte et se connecter avec sur l’application, également le test de crash applicatif n’a pas été réalisé.
## Ajouts
-	Nous avons réalisé une activité de démarrage qui vérifie la connexion de l’utilisateurs.
-	Si un container prend trop de place sur un bateau, il se retrouve directement dans le port le plus proche (un ajout de tableau de référence au conteneur et donc ajouté au port)
-	L’utilisateur peut consulter les ports, les containers se trouvant à l’intérieur et les déplacés sur des bateaux (seulement si ceux-ci on l’a place nécessaire)
-	Les marqueurs de la map sont customisé 
-	La map affiche tous les bateaux et ports simultanément
### Calcul distance du port
Pour calculer la distance du port le plus proche nous avons utilisé les règles de calculs des distances de vecteur.
```
double calcdistance = Math.sqrt(
                    ((getLongitude()-port.getLongitude())*(getLongitude()-port.getLongitude()))
                            +
                            ((getLatitude()-port.getLatitude())*(getLatitude()-port.getLatitude()))
            );
```
