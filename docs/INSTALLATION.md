# CanoStats : installation et mise en route sur un poste de développement

Ce guide explique comment faire tourner l'application **CanoStats** (statistiques des activités de l'épicerie La Canopée) sur un PC, en partant d'une machine vierge. Il s'adresse à un informaticien qui reprend le projet.

- Dépôt : <https://github.com/GregGaoter/CanoStats-App>
- Système de référence : **Windows 10/11**. Les différences pour macOS et Linux sont indiquées quand il y en a.
- Durée d'une première installation : environ 1 h, dont une bonne partie en téléchargements (Maven et npm).

> Le dépôt `GregGaoter/CanoStats-JHipster` est une ancienne ébauche (6 commits). Le projet à reprendre est **CanoStats-App**.

---

## Sommaire

1. [Vue d'ensemble](#1-vue-densemble)
2. [Prérequis](#2-prérequis)
3. [Installer les outils](#3-installer-les-outils)
4. [Récupérer le code](#4-récupérer-le-code)
5. [Préparer la base PostgreSQL](#5-préparer-la-base-postgresql)
6. [Configurer les variables d'environnement](#6-configurer-les-variables-denvironnement)
7. [Premier lancement en développement](#7-premier-lancement-en-développement)
8. [Se connecter et charger les données](#8-se-connecter-et-charger-les-données)
9. [Lancer les tests](#9-lancer-les-tests)
10. [Construire et lancer la version de production](#10-construire-et-lancer-la-version-de-production)
11. [Dépannage](#11-dépannage)
12. [Points d'attention pour la reprise](#12-points-dattention-pour-la-reprise)
13. [Aide-mémoire des commandes](#13-aide-mémoire-des-commandes)

---

## 1. Vue d'ensemble

CanoStats est un **monolithe JHipster 8.7.3** : un seul projet contient le backend Spring Boot et le frontend React, et la construction produit un seul fichier `.jar`.

| Couche | Technologie | Version |
|---|---|---|
| Langage backend | Java | **17** (le build accepte aussi 21 et 23) |
| Framework backend | Spring Boot | 3.3.5 |
| Build backend | Maven, via le wrapper `mvnw` | 3.9.9 (téléchargé automatiquement) |
| Base de données | PostgreSQL | 17 (image Docker `postgres:17.0`) |
| Migrations de schéma | Liquibase | intégré, exécuté au démarrage |
| Authentification | JWT (comptes stockés en base) | — |
| Envoi d'e-mails | SendGrid (API HTTP) | `sendgrid-java` |
| Frontend | React + TypeScript + PrimeReact + Chart.js | React 18.3, TS 5.6 |
| Build frontend | Webpack, via le wrapper `npmw` | Node **22.11.0** / npm 10.9.0 (téléchargés automatiquement) |
| Tests backend | JUnit + Testcontainers (PostgreSQL dans Docker) | — |
| Tests frontend | Jest + ESLint | — |

### Ports utilisés en développement

| Port | Service |
|---|---|
| `5432` | PostgreSQL |
| `8080` | Backend Spring Boot (API REST, et frontend compilé quand il a été construit par Maven) |
| `9060` | Serveur de développement Webpack (frontend avec rechargement à chaud, redirige `/api` vers 8080) |
| `9000` | BrowserSync (devant le 9060, synchronise plusieurs navigateurs) |

### Fonctionnement en bref

1. Le backend lit sa configuration dans `src/main/resources/config/application*.yml`. Les secrets (base de données, JWT, SendGrid) **ne sont pas dans le dépôt** : ils viennent de variables d'environnement, regroupées dans le fichier `cano-stats-env.source`.
2. Au démarrage, Liquibase crée ou met à jour les tables et insère deux comptes par défaut (`admin` et `user`).
3. Les données de l'épicerie (produits et mouvements de stock) sont **importées depuis des fichiers JSON exportés du logiciel Epicerio**, depuis l'écran *Administration > Importation*.
4. Les pages de statistiques (analyse mensuelle des mouvements, anomalies d'inventaire, plus fortes pertes, plus faibles ventes) font leurs calculs sur ces données.

---

## 2. Prérequis

**Matériel :**

- 8 Go de RAM minimum, 16 Go recommandés : Docker Desktop, le backend et Webpack tournent en même temps.
- 10 Go d'espace disque libre : JDK, Docker, dépôt Maven local (`~/.m2`), `node_modules`.

**Accès :**

- Connexion Internet sans proxy bloquant vers `repo.maven.apache.org`, `registry.npmjs.org`, `nodejs.org`, `docker.io` et `github.com`.
- Droits administrateur sur le PC pour installer les outils.
- Le fichier **`cano-stats-env.source`**, transmis à part (il contient des secrets).

---

## 3. Installer les outils

Sous Windows, tout s'installe avec **winget**, déjà présent sur Windows 10/11 récents. Ouvrir **PowerShell en tant qu'administrateur**, puis lancer les commandes ci-dessous.

### 3.1 Git (obligatoire)

```powershell
winget install --id Git.Git -e
```

Git pour Windows fournit aussi **Git Bash**, utile pour le script `npm run backend` (voir §7).

Après l'installation, fermer et rouvrir PowerShell, puis :

```powershell
git config --global core.longpaths true
```

Ce réglage évite les erreurs *Filename too long* dans `node_modules`.

Si vous comptez committer, configurez aussi votre identité :

```powershell
git config --global user.name "Prénom Nom"
```

```powershell
git config --global user.email "vous@example.com"
```

### 3.2 JDK 17 (obligatoire)

La distribution **Eclipse Temurin 17** est recommandée (c'est aussi celle du devcontainer du projet) :

```powershell
winget install --id EclipseAdoptium.Temurin.17.JDK -e
```

L'installeur Temurin propose l'option *Set JAVA_HOME variable* : la cocher. Sinon, définir `JAVA_HOME` à la main (Paramètres Windows > Système > Informations système > Paramètres avancés > Variables d'environnement), par exemple `C:\Program Files\Eclipse Adoptium\jdk-17.x.x-hotspot`, et ajouter `%JAVA_HOME%\bin` au `Path`.

Vérification, dans un **nouveau** terminal :

```powershell
java -version
```

La sortie doit afficher `17.x`. Le build refuse les versions 18, 19, 20 et 22 (règle Maven Enforcer : 17, 21 ou 23 uniquement).

> **Maven n'a pas besoin d'être installé** : le wrapper `mvnw` télécharge Maven 3.9.9 au premier lancement.

### 3.3 Docker Desktop (fortement recommandé)

Docker sert à deux choses :

- faire tourner PostgreSQL sans l'installer (§5, option A) ;
- lancer les **tests backend**, qui démarrent eux-mêmes un PostgreSQL jetable via Testcontainers (§9). **Sans Docker, `mvnw verify` échoue.**

```powershell
winget install --id Docker.DockerDesktop -e
```

Redémarrer le PC si l'installeur le demande, puis lancer Docker Desktop et accepter l'activation de WSL 2 si elle est proposée.

Vérification :

```powershell
docker info
```

La commande doit afficher une section `Server:` sans erreur. Si elle répond *cannot find the file specified* / *pipe/dockerDesktopLinuxEngine*, c'est que Docker Desktop n'est pas démarré.

### 3.4 PostgreSQL natif (seulement si vous n'utilisez pas Docker)

```powershell
winget install --id PostgreSQL.PostgreSQL.17 -e
```

Retenez le mot de passe du super-utilisateur `postgres` demandé pendant l'installation. Ajoutez `C:\Program Files\PostgreSQL\17\bin` au `Path` pour disposer de `psql`.

### 3.5 Node.js 22 (facultatif mais conseillé)

Le projet **n'a pas besoin** d'un Node global : Maven installe Node 22.11.0 et npm 10.9.0 dans `target/node/`, et le wrapper `npmw` les utilise. Un Node global reste pratique pour l'éditeur (ESLint, Prettier, Jest) et les hooks Git (husky).

Pour garder exactement la même version que le projet, passez par **nvm-windows** :

```powershell
winget install --id CoreyButler.NVMforWindows -e
```

Puis, dans un nouveau terminal :

```powershell
nvm install 22.11.0
```

```powershell
nvm use 22.11.0
```

Le projet exige Node **22.11.0 ou plus récent** (`engines` dans `package.json`).

### 3.6 Éditeur de code (au choix)

- **IntelliJ IDEA** (Community suffit, Ultimate est plus confortable pour Spring) : ouvrir le dossier, le projet Maven est détecté. Le plugin **EnvFile** permet de charger `cano-stats-env.source` dans une configuration de lancement.
- **VS Code**, avec les extensions proposées par le devcontainer du projet :
  `vscjava.vscode-java-pack`, `vmware.vscode-boot-dev-pack`, `dbaeumer.vscode-eslint`, `esbenp.prettier-vscode`, `firsttris.vscode-jest-runner`, `christian-kohler.npm-intellisense`.

> **Option devcontainer** : le dépôt contient un `.devcontainer/` (Java 17, Node 22.11.0). Avec VS Code, l'extension *Dev Containers* et Docker, *Reopen in Container* fournit tout l'outillage sans rien installer d'autre. Les variables d'environnement (§6) restent à fournir dans le conteneur.

### 3.7 Équivalents macOS / Linux

| Outil | macOS (Homebrew) | Debian/Ubuntu |
|---|---|---|
| Git | `brew install git` | `sudo apt install git` |
| JDK 17 | `brew install --cask temurin@17` | `sudo apt install openjdk-17-jdk` |
| Docker | `brew install --cask docker` | Docker Engine (docs.docker.com) |
| Node 22 | `brew install nvm` puis `nvm install 22.11.0` | `nvm install 22.11.0` |

Sous macOS/Linux, remplacer `.\mvnw.cmd` par `./mvnw` et `.\npmw.cmd` par `./npmw` dans tout le guide.

---

## 4. Récupérer le code

Le fichier de variables doit se trouver dans le **dossier parent** du projet : c'est là que le script `npm run backend` le cherche (`source ../cano-stats-env.source`). On crée donc un dossier de travail qui contiendra les deux.

```powershell
New-Item -ItemType Directory -Force C:\dev\canostats
```

```powershell
cd C:\dev\canostats
```

```powershell
git clone https://github.com/GregGaoter/CanoStats-App.git
```

Copier ensuite le fichier `cano-stats-env.source` reçu dans `C:\dev\canostats\`. Arborescence attendue :

```
C:\dev\canostats\
├── cano-stats-env.source        ← secrets, hors du dépôt Git
└── CanoStats-App\               ← le dépôt cloné
    ├── pom.xml
    ├── package.json
    ├── mvnw / mvnw.cmd
    ├── npmw / npmw.cmd
    └── src\
```

> Passez par `git clone` plutôt que par le bouton *Download ZIP* : le hook npm `prepare` (husky) et le plugin Maven `git-commit-id` attendent un vrai dépôt Git.

### Organisation du code

| Chemin | Contenu |
|---|---|
| `src/main/java/ch/epicerielacanopee/statistics/` | Backend : `domain` (entités JPA), `repository`, `service` (calculs statistiques dans `service/util`), `web/rest` (API REST), `config` (sécurité, SendGrid, etc.) |
| `src/main/resources/config/` | `application.yml` (commun), `application-dev.yml`, `application-prod.yml`, `application-tls.yml` |
| `src/main/resources/config/liquibase/` | `master.xml` et `changelog/` (schéma), `data/` (comptes et rôles par défaut) |
| `src/main/webapp/app/` | Frontend React : `modules/statistics` (pages de statistiques), `modules/administration/file-import` (import JSON), `entities` (écrans CRUD Produit / MouvementsStock) |
| `src/main/docker/` | Fichiers Docker Compose : `postgresql.yml`, `services.yml`, `app.yml`, monitoring, Sonar |
| `src/test/` | Tests backend (Java) et frontend (Jest) |
| `webpack/` | Configuration Webpack (dev et prod) |
| `.jhipster/`, `.yo-rc.json`, `statistics.jdl` | Configuration du générateur JHipster et modèle des entités |

---

## 5. Préparer la base PostgreSQL

Il faut une base PostgreSQL accessible avant de lancer le backend. **Le schéma n'a pas à être créé à la main** : Liquibase le crée au premier démarrage.

Commencez par regarder la valeur de `DATABASE_URL` dans `cano-stats-env.source` :

- si elle pointe vers `localhost`, préparez une base locale avec l'**option A** (Docker) ou l'**option B** (PostgreSQL natif) ;
- si elle pointe vers un serveur distant (base hébergée), il n'y a rien à installer : vérifiez seulement que ce serveur est joignable depuis le PC, puis passez au §6.

### Option A : PostgreSQL dans Docker (recommandé)

Le projet fournit `src/main/docker/postgresql.yml` : PostgreSQL 17, utilisateur `statistics`, mot de passe `password`, base `statistics`, port `127.0.0.1:5432`.

**Avant le premier lancement, activez la persistance des données.** Sans volume, la base est perdue dès que le conteneur est supprimé. Dans `src/main/docker/postgresql.yml`, décommentez les deux lignes `volumes` :

```yaml
    volumes:
      - ~/volumes/jhipster/statistics/postgresql/:/var/lib/postgresql/data/
```

Démarrage, depuis `C:\dev\canostats\CanoStats-App` :

```powershell
docker compose -f src/main/docker/postgresql.yml up -d --wait
```

Vérification :

```powershell
docker compose -f src/main/docker/postgresql.yml ps
```

Le service `postgresql` doit être `healthy`.

Arrêt **sans** perte de données :

```powershell
docker compose -f src/main/docker/postgresql.yml stop
```

> ⚠️ `npm run docker:db:down` exécute `docker compose ... down -v`, ce qui **supprime le conteneur et ses volumes anonymes**. Ne l'utilisez que pour repartir d'une base vide.

Valeurs à mettre dans `cano-stats-env.source` avec cette option :

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/statistics
export DATABASE_USERNAME=statistics
export DATABASE_PASSWORD=password
```

### Option B : PostgreSQL installé sur le PC

Créer l'utilisateur et la base (adaptez le mot de passe) :

```powershell
psql -U postgres -h localhost -c "CREATE ROLE statistics WITH LOGIN PASSWORD 'mot_de_passe_a_choisir';"
```

```powershell
psql -U postgres -h localhost -c "CREATE DATABASE statistics OWNER statistics ENCODING 'UTF8';"
```

Puis reporter ces valeurs dans `DATABASE_URL`, `DATABASE_USERNAME` et `DATABASE_PASSWORD`.

> Si un PostgreSQL natif tourne déjà sur le port 5432, le conteneur Docker de l'option A ne pourra pas démarrer. Utilisez l'un ou l'autre, pas les deux.

### Ce que Liquibase crée au premier démarrage

- Tables JHipster : `jhi_user`, `jhi_authority`, `jhi_user_authority`.
- Tables métier : `mouvements_stock` (contrainte d'unicité sur `epicerio_id`, plusieurs index) et `produit` (index sur `code`).
- Rôles `ROLE_ADMIN` et `ROLE_USER`, et deux comptes :

| Identifiant | Mot de passe | Rôles |
|---|---|---|
| `admin` | `admin` | ROLE_ADMIN, ROLE_USER |
| `user` | `user` | ROLE_USER |

Les données fictives (contexte Liquibase `faker`) **ne sont pas chargées** : en dev, le contexte actif est `dev`. La base démarre donc vide de produits et de mouvements.

---

## 6. Configurer les variables d'environnement

### 6.1 Les variables attendues

Le backend **ne démarre pas** s'il manque une de ces variables (erreur `Could not resolve placeholder ...`).

| Variable | Utilisée dans | Rôle | Exemple / format |
|---|---|---|---|
| `DATABASE_URL` | `application.yml` → `spring.datasource.url` | URL JDBC de la base | `jdbc:postgresql://localhost:5432/statistics` |
| `DATABASE_USERNAME` | `application.yml` → `spring.datasource.username` | Utilisateur PostgreSQL | `statistics` |
| `DATABASE_PASSWORD` | `application.yml` → `spring.datasource.password` | Mot de passe PostgreSQL | — |
| `JWT_BASE64_SECRET_DEV` | `application-dev.yml` → `jhipster.security.authentication.jwt.base64-secret` | Clé de signature des jetons JWT (profil dev) | Base64, au moins 256 bits |
| `SENDGRID_API_KEY` | `application.yml` → `application.sendgrid.api.key` | Clé API SendGrid | `SG.xxxx...` (une valeur factice suffit pour démarrer) |
| `SENDGRID_FROM_EMAIL` | `application.yml` → `application.sendgrid.from.email` | Adresse d'expédition des e-mails | Adresse vérifiée dans SendGrid |

Variable facultative, **pour la production** : `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` remplace la clé JWT écrite en dur dans `application-prod.yml` (voir §12).

### 6.2 Le fichier `cano-stats-env.source`

C'est un fichier shell : une ligne `export NOM=valeur` par variable. Un modèle commenté se trouve dans [`docs/cano-stats-env.source.example`](cano-stats-env.source.example).

Règles à respecter :

1. Il est placé dans le **dossier parent** du dépôt (`C:\dev\canostats\cano-stats-env.source`).
2. Il est enregistré avec des fins de ligne **LF**, pas CRLF. Dans VS Code, cliquer sur `CRLF` dans la barre d'état et choisir `LF`. Avec CRLF, bash garde un `\r` invisible en fin de valeur et l'authentification PostgreSQL échoue.
3. Il **n'est jamais commité** : le dépôt est public.

Générer une clé JWT :

```bash
openssl rand -base64 64 | tr -d '\n'
```

(commande pour Git Bash ; en PowerShell, voir la commande donnée dans le fichier modèle)

**SendGrid** n'est utilisé que pour les e-mails : activation de compte après inscription, création de compte par un admin, réinitialisation de mot de passe. Avec des valeurs factices, l'application fonctionne normalement mais ces e-mails ne partent pas : l'envoi échoue en tâche de fond et apparaît dans les logs. Pour de vrais envois : créer un compte SendGrid, une clé API avec le droit *Mail Send*, et vérifier l'adresse d'expédition (*Sender Authentication*).

### 6.3 Charger les variables dans le terminal

Les variables ne valent que pour le terminal où elles ont été chargées. Il faut donc les recharger dans chaque nouveau terminal qui lance le backend.

**Git Bash :**

```bash
source ../cano-stats-env.source
```

**PowerShell** (à coller dans la session, depuis le dossier `CanoStats-App`) :

```powershell
Get-Content ..\cano-stats-env.source | ForEach-Object { $l = $_.Trim(); if ($l -and -not $l.StartsWith('#')) { $l = $l -replace '^export\s+', ''; $k, $v = $l -split '=', 2; Set-Item -Path "env:$($k.Trim())" -Value $v.Trim().Trim('"').Trim("'") } }
```

Vérification (PowerShell) :

```powershell
Get-ChildItem env: | Where-Object Name -Match 'DATABASE|JWT|SENDGRID' | Select-Object Name
```

**IntelliJ IDEA** : dans la configuration de lancement `StatisticsApp`, onglet *EnvFile*, ajouter `../cano-stats-env.source` (plugin EnvFile). Autre solution : renseigner les variables une à une dans *Environment variables*.

---

## 7. Premier lancement en développement

Tout se lance depuis `C:\dev\canostats\CanoStats-App`. Le premier lancement est long, souvent 5 à 15 minutes : Maven télécharge ses dépendances dans `~/.m2`, puis Node, npm et les paquets npm dans `target/node` et `node_modules`.

PostgreSQL doit tourner (§5) et les variables doivent être chargées dans le terminal du backend (§6.3).

### Méthode 1 : tout via Maven (la plus simple)

Dans un terminal où les variables sont chargées :

```powershell
.\mvnw.cmd
```

Sans argument, cette commande exécute le goal par défaut `spring-boot:run` avec les profils Maven `dev` et `webapp`, actifs par défaut. Elle :

1. installe Node 22.11.0 et npm dans `target/node/` ;
2. lance `npm install` puis `npm run webapp:build` (frontend compilé dans `target/classes/static/`) ;
3. compile le Java et démarre Spring Boot avec le profil `dev` (qui inclut `api-docs`) ;
4. applique les migrations Liquibase.

Le démarrage est terminé quand les logs affichent :

```
Application 'CanoStats' is running! Access URLs:
    Local:      http://localhost:8080/
```

Ouvrir <http://localhost:8080>.

Le frontend n'est recompilé par Maven que si ses sources ont changé (Maven compare une somme de contrôle dans `target/checksums.csv`). Pour développer le frontend, la méthode 2 est plus pratique.

### Méthode 2 : backend et frontend séparés (développement au quotidien)

On lance deux terminaux, et le frontend se recharge à chaque modification.

**Terminal 1 : backend seul**, sans recompiler le frontend.

Depuis **Git Bash** (c'est le script prévu par le projet : il charge `../cano-stats-env.source` puis lance `./mvnw -P-webapp`) :

```bash
./npmw run backend
```

Ou depuis **PowerShell**, après avoir chargé les variables (§6.3) :

```powershell
.\mvnw.cmd "-P-webapp"
```

> `npm run backend` appelle `bash -c '...'`. Lancé depuis PowerShell ou cmd, `bash` peut ne pas exister, ou correspondre au bash de WSL, qui ne verra ni le JDK Windows ni le wrapper. Sous Windows, lancez ce script **depuis Git Bash** ou utilisez la variante PowerShell ci-dessus.

**Terminal 2 : frontend** (pas besoin de variables d'environnement).

À faire une fois, puis à chaque modification de `package.json` :

```powershell
.\npmw.cmd install
```

Puis :

```powershell
.\npmw.cmd start
```

Ouvrir <http://localhost:9060>, ou <http://localhost:9000> pour passer par BrowserSync. Le serveur Webpack redirige `/api`, `/management` et `/v3/api-docs` vers le backend sur le port 8080.

> `.\npmw.cmd` utilise le Node installé par Maven dans `target/node/`. S'il n'existe pas encore, il lance lui-même `mvnw -Pwebapp frontend:install-node-and-npm`. Si Node 22 est installé globalement, `npm install` et `npm start` fonctionnent aussi.

### Vérifier que tout fonctionne

| Vérification | Résultat attendu |
|---|---|
| <http://localhost:8080/management/health> | `{"status":"UP"}`, avec plus de détails une fois connecté en admin |
| Page d'accueil | Écran de connexion CanoStats |
| Connexion `admin` / `admin` | Barre de menu *Tableau de bord / Statistiques / Tables / Administration* |
| <http://localhost:8080/admin/docs> (profil `api-docs`, connecté en admin) | Documentation Swagger de l'API REST |

> La barre de menu ne montre que les écrans métier et *Administration > Importation*. Les écrans JHipster standard restent accessibles **par leur URL** : `/account/password` (changer son mot de passe), `/account/settings`, `/admin/user-management`, `/admin/health`, `/admin/metrics`, `/admin/configuration`, `/admin/logs`, `/admin/docs`.

---

## 8. Se connecter et charger les données

### 8.1 Première connexion

1. Se connecter avec `admin` / `admin`.
2. **Changer immédiatement le mot de passe** depuis l'URL `/account/password` (par exemple <http://localhost:8080/account/password>). Faire de même pour le compte `user`, ou le désactiver depuis `/admin/user-management`. Ces écrans ne figurent pas dans la barre de menu.

### 8.2 Importer les données Epicerio

Les statistiques sont calculées à partir de deux jeux de données, exportés du logiciel de caisse/gestion **Epicerio** au format **JSON** : un tableau d'objets.

Écran : **Administration > Importation** (`/admin/file-import`).

1. Choisir le type d'entité : **Produits** ou **Mouvements de stock**.
2. Sélectionner le fichier `.json` (bouton rond avec l'icône image), puis lancer l'envoi (bouton vert avec l'icône nuage).
3. Un message affiche le nombre d'enregistrements importés, ou l'erreur rencontrée.

Taille maximale d'un fichier : **50 Mo** (`spring.servlet.multipart.max-file-size`).

| Import | Comportement | Champs JSON attendus |
|---|---|---|
| **Produits** (`POST /api/produits/import`) | **Remplace** tous les produits : la table est vidée puis rechargée. | `id`, `nom`, `code`, `disponible`, `prixFournisseur`, `htTtc`, `tauxTva`, `margeProfit`, `prixVente`, `vendu`, `quantiteParPiece`, `unite`, `prixParUnite`, `description`, `remarquesInternes`, `fournisseur`, `refFournisseur`, `stock`, `commandesClients`, `derniereVerificationDate`, `derniereLivraisonDate`, `achatFournisseur`, `dernierAchatDate`, `dernierAchatQuantite`, `statsLivraison`, `statsPerte`, `statsVente`, `statsVenteSpeciale`, `tags` |
| **Mouvements de stock** (`POST /api/mouvements-stocks/import`) | **Ajoute** les mouvements. L'`id` Epicerio est stocké dans `epicerio_id`, qui est **unique** : si un seul mouvement du fichier est déjà en base, tout l'import est annulé. L'écran affiche la date du dernier mouvement importé ; n'exporter que les mouvements postérieurs. | `id`, `date`, `utilisateur`, `type`, `mouvement`, `solde`, `vente`, `codeProduit`, `produit`, `responsableProduit`, `fournisseurProduit`, `codeFournisseur`, `reduction`, `ponderation`, `venteChf`, `valeurChf`, `remarques` |

Formats des dates : `date` (mouvements) est un instant ISO-8601, par exemple `"2025-01-31T14:22:05Z"` ; les champs `...Date` des produits sont des dates simples, par exemple `"2025-01-31"`.

Ordre conseillé pour une base neuve : d'abord **Produits**, puis **Mouvements de stock**. L'analyse « Produits les plus en perte » regroupe les mouvements par type de produit, déduit du préfixe alphabétique des codes produits.

---

## 9. Lancer les tests

**Backend** : Docker doit être démarré, car Testcontainers lance un PostgreSQL temporaire. Les tests ont leur propre configuration (`src/test/resources/config/`) et n'ont pas besoin du fichier `.source`.

```powershell
.\mvnw.cmd verify
```

**Frontend** (ESLint puis Jest) :

```powershell
.\npmw.cmd test
```

**Qualité de code :**

```powershell
.\npmw.cmd run lint
```

```powershell
.\npmw.cmd run prettier:check
```

Un hook Git (husky + lint-staged) formate automatiquement les fichiers modifiés à chaque commit.

---

## 10. Construire et lancer la version de production

### 10.1 Fichier JAR

```powershell
.\mvnw.cmd -Pprod clean verify -DskipTests
```

Ce build produit `target/statistics-0.0.1-SNAPSHOT.jar`, qui contient le frontend minifié. Retirer `-DskipTests` pour lancer aussi les tests (Docker requis).

Lancement, dans un terminal où les variables sont chargées :

```powershell
java -jar target\statistics-0.0.1-SNAPSHOT.jar
```

Le profil Spring `prod` est intégré au JAR. Ouvrir <http://localhost:8080>.

Différences avec le profil dev :

- `JWT_BASE64_SECRET_DEV` n'est **pas** utilisé. La clé vient de `application-prod.yml`, sauf si `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` est définie, ce qui est indispensable (voir §12).
- Contexte Liquibase `prod`, cache HTTP long, compression activée, pas de Swagger, sauf si le JAR est construit avec `-Papi-docs`.
- `jhipster.mail.base-url` vaut `http://my-server-url-to-change` dans `application-prod.yml` : il faut le remplacer par l'URL réelle du serveur, sinon les liens des e-mails seront faux.

### 10.2 Image Docker (facultatif)

```powershell
.\npmw.cmd run java:docker
```

```powershell
docker compose -f src/main/docker/app.yml up -d --wait
```

> ⚠️ En l'état, `src/main/docker/app.yml` ne transmet au conteneur ni `DATABASE_USERNAME`, ni `DATABASE_PASSWORD`, ni `SENDGRID_API_KEY`, ni `SENDGRID_FROM_EMAIL`, ni la clé JWT de production. **L'application ne démarrera pas** tant que ces variables ne sont pas ajoutées dans la section `environment:` du service `app`, ou fournies via `env_file:`.

---

## 11. Dépannage

| Symptôme | Cause probable | Solution |
|---|---|---|
| `Could not resolve placeholder 'DATABASE_URL'` (ou `SENDGRID_FROM_EMAIL`, `JWT_BASE64_SECRET_DEV`...) | Variables non chargées dans **ce** terminal | Recharger le `.source` (§6.3) dans le terminal qui lance le backend. |
| `You are running an incompatible version of Java. JHipster supports JDK 17 to 23.` | Mauvais JDK dans le `PATH` ou `JAVA_HOME` | `java -version` doit afficher 17, 21 ou 23. Corriger `JAVA_HOME`. |
| `JAVA_HOME not found in your environment` (au lancement de `mvnw`) | `JAVA_HOME` non défini | Voir §3.2, puis ouvrir un nouveau terminal. |
| `Connection to localhost:5432 refused` | PostgreSQL arrêté | `docker compose -f src/main/docker/postgresql.yml up -d --wait`, ou démarrer le service PostgreSQL Windows. |
| `password authentication failed for user "statistics"` | Mauvais mot de passe, ou `\r` en fin de ligne dans le `.source` | Vérifier la valeur, convertir le fichier en LF (§6.2). |
| `Bind for 127.0.0.1:5432 failed: port is already allocated` | Un PostgreSQL natif occupe déjà le port | Arrêter l'un des deux, ou changer le port (`5433:5432`) et `DATABASE_URL`. |
| `Port 8080 was already in use` | Une autre application (ou un ancien backend) tourne | `Get-NetTCPConnection -LocalPort 8080` pour identifier le processus, puis l'arrêter. |
| `Validation Failed: ... checksum changed` (Liquibase) | Un fichier de changelog déjà appliqué a été modifié | Ne jamais modifier un changelog existant : en créer un nouveau. En dev, on peut repartir d'une base vide. |
| `ERROR: duplicate key value violates unique constraint "mouvements_stock_epicerio_id_key"` (import) | Le fichier contient des mouvements déjà importés | N'exporter d'Epicerio que les mouvements postérieurs à la date affichée sur l'écran d'import. |
| `Could not find a valid Docker environment` (tests) | Docker Desktop arrêté | Démarrer Docker Desktop, vérifier avec `docker info`. |
| `'bash' n'est pas reconnu...` ou erreur WSL avec `npm run backend` | Script lancé depuis PowerShell/cmd | Le lancer depuis Git Bash, ou utiliser `.\mvnw.cmd "-P-webapp"` (§7). |
| `husky - .git can't be found` pendant `npm install` | Projet récupéré en ZIP | Cloner avec `git clone` (§4). |
| `Filename too long` | Chemins Windows > 260 caractères | `git config --global core.longpaths true`, et un dossier de travail court (`C:\dev\...`). |
| Le frontend servi sur 8080 n'affiche pas les dernières modifications | Maven a sauté la recompilation (sommes de contrôle inchangées) | `.\npmw.cmd run webapp:build`, ou supprimer `target\checksums.csv`, ou utiliser la méthode 2 (port 9060). |
| Page blanche sur 9060 avec erreurs 401/502 dans la console | Backend non démarré | Démarrer le terminal 1 et attendre le message `Application 'CanoStats' is running!`. |
| Les e-mails d'inscription ou de réinitialisation n'arrivent pas | Clé SendGrid factice ou expéditeur non vérifié | Configurer SendGrid (§6.2) ; l'erreur apparaît dans les logs du backend (`MailService`). |
| Premier `mvnw` très lent ou bloqué | Téléchargements, proxy ou antivirus | Attendre ; configurer le proxy dans `~/.m2/settings.xml` ; exclure `~/.m2` et le dossier du projet de l'analyse antivirus en temps réel. |

---

## 12. Points d'attention pour la reprise

Ces points ont été relevés en relisant le code. Ils n'empêchent pas de faire tourner l'application en local, mais **ils sont à corriger avant toute mise en ligne**.

1. **API REST ouverte sans authentification.** Dans `config/SecurityConfiguration.java`, les règles `GET /api/**` et `POST /api/**` sont en `permitAll()` et placées **avant** les règles `/api/admin/**` (ROLE_ADMIN) et `/api/**` (authentifié). Spring Security applique la première règle qui correspond. Résultat : toutes les requêtes GET et POST de l'API, y compris `/api/admin/users` et les imports de fichiers, passent sans être connecté. Seuls PUT, PATCH et DELETE sont protégés.
2. **Clé JWT de production publiée.** La clé `base64-secret` de `application-prod.yml` est en clair dans le dépôt public (on la retrouve aussi dans `.yo-rc.json` et dans la configuration de test). En production, définir `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` avec une clé neuve. Sinon, n'importe qui peut fabriquer un jeton administrateur valide.
3. **Comptes par défaut** `admin`/`admin` et `user`/`user`, créés par Liquibase : changer les mots de passe dès la première connexion.
4. **Configuration Docker de développement uniquement** : mot de passe `password` et `POSTGRES_HOST_AUTH_METHOD=trust` dans `postgresql.yml`, base sans volume par défaut. `app.yml` est incomplet (§10.2).
5. **Valeurs à adapter** : `MailService` envoie les e-mails sous le nom « CanoStats », écrit en dur dans le code, alors que `application.yml` définit aussi un `application.sendgrid.from.name`. `jhipster.mail.base-url` vaut encore la valeur par défaut en prod.
6. **Version de JHipster** : le projet a été généré avec JHipster 8.7.3 (`.yo-rc.json`). Pour régénérer des entités ou mettre à jour le projet, utiliser cette version : `npx generator-jhipster@8.7.3`. Le modèle des entités est dans `statistics.jdl`.

---

## 13. Aide-mémoire des commandes

Toutes les commandes se lancent depuis `C:\dev\canostats\CanoStats-App`.

| Action | Commande (PowerShell) |
|---|---|
| Démarrer PostgreSQL (Docker) | `docker compose -f src/main/docker/postgresql.yml up -d --wait` |
| Arrêter PostgreSQL (données conservées) | `docker compose -f src/main/docker/postgresql.yml stop` |
| Charger les variables | voir §6.3 |
| Backend + frontend compilé (port 8080) | `.\mvnw.cmd` |
| Backend seul | `.\mvnw.cmd "-P-webapp"` (ou `./npmw run backend` dans Git Bash) |
| Installer les dépendances frontend | `.\npmw.cmd install` |
| Frontend avec rechargement à chaud (port 9060) | `.\npmw.cmd start` |
| Tests backend (Docker requis) | `.\mvnw.cmd verify` |
| Tests frontend | `.\npmw.cmd test` |
| Lint / formatage | `.\npmw.cmd run lint` / `.\npmw.cmd run prettier:format` |
| JAR de production | `.\mvnw.cmd -Pprod clean verify -DskipTests` |
| Lancer le JAR | `java -jar target\statistics-0.0.1-SNAPSHOT.jar` |
| Image Docker | `.\npmw.cmd run java:docker` |
| Lister tous les scripts npm | `.\npmw.cmd run` |

Documentation de référence : [JHipster 8.7.3](https://www.jhipster.tech/documentation-archive/v8.7.3), en particulier les pages *Using JHipster in development* et *Using JHipster in production*.
