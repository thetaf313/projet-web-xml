# Gestion des Filieres et des Classes

Ce projet est une application web développée avec Spring Boot et Thymeleaf. Il permet de gérer des filières et des classes, incluant des fonctionnalités CRUD (Créer, Lire, Mettre à jour, Supprimer) pour chaque entité. L'application utilise JAXB (Java Architecture for XML Binding) pour manipuler un fichier XML qui sert de base de données, facilitant la sérialisation et la désérialisation des objets Java en XML et inversement. Cela permet de stocker et de gérer les données de manière structurée sans utiliser de base de données relationnelle classique. L'interface utilisateur est construite avec Bootstrap 5, offrant une navigation simple et intuitive.

## Fonctionnalités

- Gestion des filières :
    - Créer une nouvelle filière
    - Voir la liste des filières
    - Mettre à jour les détails d'une filière
    - Supprimer une filière
- Gestion des classes :
    - Créer une nouvelle classe
    - Voir la liste des classes
    - Mettre à jour les détails d'une classe
    - Supprimer une classe

## Technologies utilisées

- **Backend :**
    - Java
    - Spring Boot
    - Thymeleaf (pour le rendu côté serveur)

- **Frontend :**
    - Bootstrap 5
    - FontAwesome (pour les icônes)

- **Base de données :**
    - Utilisation d'un fichier XML comme base de données

## Prérequis

- Java 17+
- Maven 3+
- Un IDE comme IntelliJ IDEA ou Eclipse
- Un serveur local comme XAMPP pour exécuter l'application

## Installation et exécution

1. **Cloner le projet depuis le référentiel GitHub** :
   ```bash
   git clone https://github.com/votre-utilisateur/nom-du-projet.git
   cd nom-du-projet
2. Configurer le projet : Assurez-vous que vous avez installé Java, Maven et que vous êtes dans un environnement configuré.

3. Exécuter l'application : Vous pouvez exécuter l'application avec Maven en utilisant la commande suivante :
   mvn spring-boot:run ou faire un run du projet depuis votre IDE (par exemple IntelliJ IDEA)
4. Accéder à l'application : Ouvrez votre navigateur et accédez à http://localhost:8081

# Utilisation

## Gestion des Filieres
Accédez à la page de gestion des filières via le menu de navigation.
Cliquez sur "Add Filiere" pour ajouter une nouvelle filière.
Voir, modifier ou supprimer une filière existante.

## Gestion des Classes
Accédez à la page de gestion des classes via le menu de navigation.
Cliquez sur "Add Classe" pour ajouter une nouvelle classe.
Voir, modifier ou supprimer une classe existante.

# Contribution
Si vous souhaitez contribuer au projet, veuillez suivre ces étapes :

# Fork le projet
Créez une nouvelle branche (git checkout -b feature/amélioration)
Committez vos modifications (git commit -m 'Ajout d'une nouvelle fonctionnalité')
Poussez vos modifications (git push origin feature/amélioration)
Ouvrez une Pull Request

# Auteurs 
Team Master 1 UDB

License
Ce projet est sous licence MIT - voir le fichier LICENSE pour plus de détails.


### Explications des sections :
1. **Fonctionnalités** : Résume les actions principales de ton application (gestion des filières et des classes).
2. **Technologies utilisées** : Liste des technologies employées dans le projet.
3. **Prérequis** : Indique les outils nécessaires pour exécuter le projet.
4. **Installation et exécution** : Fournit les étapes pour installer et démarrer l'application.
5. **Utilisation** : Guide rapide sur la façon d'utiliser l'application.
6. **Contribution** : Instructions pour ceux qui souhaitent contribuer au projet.
7. **Auteurs** : Liste des contributeurs principaux.
8. **License** : Mention de la licence du projet.