# PacmanACL
Projet de ACL  
## Description
Un jeu mono-utilisateur avec une interface
graphique. Le jeu consiste à déplacer un personnage dans un labyrinthe dans le but de
découvrir un trésor. Dans le labyrinthe il peut y avoir des monstres de différents types qui
essayent d’attaquer le personnage.
## Membre
RALANTONISAINANA Ny Elanirina<br>
ZHOU Zixun<br>
TAGNON Ludovic<br>
VILLAUME Lucas<br>
MARTINI Florent
## Organisation du travail
Dans le branch main, les backlogs sont dans le "support".<br>
Lien de Trello : 
https://trello.com/b/VXQ6D2Gc/agile <br>
Google Drive du projet :
https://drive.google.com/drive/folders/1CuMOIeEovxx8zTQlSEQoWMFc3sZzKdx7?usp=drive_link
## Technique Utilisé
1. Maven : Générer le projet.
2. LWJGL : Light Weight Java Game Library<br>
Lien pour télécharger: https://www.lwjgl.org/ <br>
Lien guide d'installation : https://youtu.be/ZR9hNrnT2QE?si=KWW3rr34g1M9ucUo

## Version Du Technique
### Maven
maven-shade-plugin : Version 3.5.1<br>
maven-resources-plugin : Version 3.2.0
### LWJGL
lwjgl.* : Version 3.3.3

## Installation du Maven
### Linux/Ubuntu
```bash
sudo apt update
sudo apt install maven
```
### macOS
```bash
brew install maven
```
## Récupérer le projet
Clonez ce dépôt :
```bash
git clone https://github.com/Zixun214/PacmanACL.git
```
## Exécution
Entrez dans le dossier où se trouve le "pom.xml":<br>
```bash
mvn clean
mvn package
```

Un répertoire "target" va être crée.<br>
Allez dans le "target".<br>
Commande Utilisé : (Windows and Linux)
```bash
Java -jar JeuTemplate-1.0-SNAPSHOT.jar
```
Commande Utilisé : (Macos)
```bash
Java -XstartOnFirstThread -jar JeuTemplate-1.0-SNAPSHOT.jar
```