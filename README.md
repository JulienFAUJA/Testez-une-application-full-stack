# :star: Yoga :star:


 <div align="center">

[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)[![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](https://forthebadge.com)[![forthebadge](https://forthebadge.com/images/badges/made-with-typescript.svg)](https://forthebadge.com)

</div>
<hr/>


## :warning: Installation ![Angular](https://img.shields.io/badge/angular_CLI-v14.1.0-blue)

Ce projet a été généré avec [Angular CLI](https://github.com/angular/angular-cli) version 14.1.0.


Pour cloner le repository Git, lancez la commande:

> git clone https://github.com/OpenClassrooms-Student-Center/P5-Full-Stack-testing

Allez dans le dossier du projet:

> cd `Testez-une-application-full-stack`

Installez les dependances:

> npm install

## Lancer l'application

### Lancez le Back-end:

Lancement en Développement:
pour lancer le Back-end dans un environnement de développement vous devez ouvrir le dossier `backend` avec le logiciel Eclipse ou autre en fonction de vos préférences et cliquer sur `run` ou `build` selon votre logiciel.

Lancement en Production:
Exécutez la commande:

> `mvn clean package`

Cela va compiler votre projet, exécuter les tests (s'il y en a), et générer le fichier JAR dans le répertoire target.

Une fois le fichier JAR généré, vous pouvez l'exécuter en utilisant la commande java -jar nom_du_fichier.jar.

### :computer: Lancez le Front-end
Attention le back doit être lancé d'abord

Exécutez `ng serve` ou `npm run start` pour démarrer le serveur de développement. Accédez à `http://localhost:4200/`. L'application se rechargera automatiquement si vous modifiez l'un des fichiers source.

---

### :construction: Construction

Exécutez `ng build` pour construire le projet. Les artefacts de construction seront stockés dans le répertoire `dist/`.
[![forthebadge](https://forthebadge.com/images/badges/works-on-my-machine.svg)](https://forthebadge.com)

---



#### :information_source: Quelques brèves explications

**npm install** : C'est une commande utilisée dans Node.js pour installer les dépendances d'un projet à partir du registre npm (Node Package Manager). Lorsque vous exécutez npm install, npm recherche le fichier package.json dans le répertoire actuel, puis installe toutes les dépendances répertoriées dans ce fichier, ainsi que leurs dépendances récursives.

**node_modules** : C'est un répertoire généré par npm qui contient toutes les dépendances installées pour un projet. Chaque dépendance est installée dans son propre répertoire à l'intérieur de node_modules. il peut être reconstruit à tout moment en utilisant le fichier package.json.

**package.json** : C'est un fichier de configuration utilisé dans les projets Node.js pour définir les métadonnées du projet ainsi que ses dépendances et scripts. Il contient des informations telles que le nom du projet, la version, la description, les auteurs, les licences, les dépendances nécessaires pour le projet, et bien plus encore. C'est également l'endroit où vous pouvez déclarer des scripts personnalisés pour automatiser des tâches de développement courantes.

**package-lock.json** (ou yarn.lock) : Ce fichier est généré automatiquement par npm (ou Yarn) pour verrouiller les versions exactes des dépendances installées dans un projet. Il garantit que les versions des dépendances sont cohérentes entre différentes installations et permet une reproductibilité précise des installations. Le fichier package-lock.json (ou yarn.lock) est utilisé pour s'assurer que les versions des dépendances ne changent pas accidentellement entre les installations, ce qui pourrait entraîner des incohérences dans le projet.

#### Quelques astuces

##### :x: Problème avec une dépendance

Si vous souhaitez supprimer une dépendance (proprement) de votre projet suivez ces étapes:

1. Supprimez le dossier **node_modules**
2. Supprimez le fichier **package-lock.json**
3. Apportez vos modification au fichier **package.json** comme par exemple supprimer la ligne d'une dépendance que vous ne voulez plus.
4. Lancez la commande `npm install`

##### :ng: Angular CLI

**Créer une application**:Angular CLI permet de créer facilement une nouvelle application Angular en utilisant la commande:
 `ng new` + nom_de_votre_projet. Cette commande génère une structure de projet initiale avec tous les fichiers et dossiers nécessaires pour démarrer le développement.
**Créer un component**:Pour créer un nouveau composant Angular, vous pouvez utiliser la commande:
 `ng generate component` + nom_de_votre_component (ou `ng g c` pour faire court). Cette commande génère automatiquement les fichiers nécessaires pour votre composant, y compris le fichier TypeScript, le fichier HTML, le fichier de style CSS ou SCSS, ainsi que les fichiers de test.
**Créer une directive**:Angular CLI simplifie la création de directives personnalisées avec la commande:
 `ng generate directive` + nom_de_votre_directive (ou `ng g d`). Cette commande crée les fichiers TypeScript nécessaires pour votre directive, ainsi que les fichiers de test associés.
**Créer un service**:Les services sont des éléments clés de toute application Angular. Pour créer un nouveau service, utilisez la commande:
 `ng generate service` + nom_de_votre_service (ou `ng g s`). Cette commande génère le fichier TypeScript pour votre service, ainsi que les fichiers de test associés.

---



## Installer la base de données MySQL

Le script SQL pour créer le schéma de la base de données se trouve dans `ressources/sql/script.sql`

### Ouvrez votre terminal ou invite de commande.

#### Création de la base

Vous devez lancer la commande suivante dans l'invite de commande si vous êtres sous Windows ou dans le terminal si vous êtes sur système UNIX (MacOs et Linux):

> mysql -u nom_utilisateur -p

Remplacez `nom_utilisateur` par votre nom d'utilisateur.

Pour créer la base de données vous devez lancer les commandes suivantes:

> CREATE DATABASE ma_base_de_donnees;
> En remplaçant `ma_base_de_donnees` par le nom de la base de données (dans ce projet le nom utilisé est `yoga`).

Puis:

> USE ma_base_de_donnees;

Toujours en remplaçant `ma_base_de_donnees` par le nom de la base de données.

#### Importez le fichier SQL

Pour importer le fichier SQL, utilisez la commande suivante:

> source ressources/sql/script.sql;

Par défaut le compte admin est:

- login: yoga@studio.com
- password: test!1234

## Test

### Tests E2E (end 2 end en Anglais / Bout en bout en Français)

lancez les tests e2e:
Allez dans le dossier front et dans la console lancez la commande:

> npm run e2e

Generer le rapport de couverture (coverage) (vous devez lancer les tests e2e d'abord):

> npm run e2e:coverage

Le Rapport est disponible dans:

> front/coverage/lcov-report/index.html

### Tests unitaires

Pour lancer les tests unitaires:

> npm run test

pour suivre les changements:

> npm run test:watch



## Index

---

#### Angular

<div id="angular">
Angular est un framework open-source développé et maintenu par Google, utilisé pour construire des applications web à grande échelle. Il s'appuie sur TypeScript, un sur-ensemble de JavaScript, et suit le modèle de conception MVC (Modèle-Vue-Contrôleur) ou plus précisément le modèle MVVM (Modèle-Vue-Modèle).
</div>

#### Framework

<div id="framework">
Un Framework (cadre de travail, ou cadriciel dans d'autres pays Francophones comme au Canada) est un ensemble structuré d'outils, de composants et de conventions de programmation conçus pour faciliter le développement d'applications logicielles. Les frameworks fournissent un cadre de travail standardisé qui permet aux développeurs de construire, déployer et maintenir des applications plus rapidement et efficacement en réutilisant des solutions prédéfinies pour des problèmes courants.
</div>

#### Java

<div id="java">
Java est un langage de programmation multiplateformes.
</div>

#### Javascript

<div id="javascript">
JavaScript est un langage de programmation de haut niveau, orienté objet et principalement utilisé pour le développement web côté client. JavaScript est un langage de programmation polyvalent et puissant, largement utilisé dans le développement web pour créer des applications interactives et dynamiques. Sa simplicité, sa flexibilité et son écosystème riche en font l'un des langages les plus populaires et les plus demandés dans l'industrie du développement logiciel.
</div>

#### Spring

<div id="spring">
Spring est un framwork Java.
</div>

#### Typescript

<div id="typescript">
TypeScript est un langage de programmation open source développé par Microsoft. Il s'agit d'un sur-ensemble de JavaScript qui ajoute des fonctionnalités de typage statique optionnel, ce qui signifie que vous pouvez spécifier le type de données des variables, des paramètres de fonction, des propriétés d'objet, etc.
</div>
