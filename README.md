# Donate Blood

Application web JEE de gestion d'une banque de sang (projet pédagogique).

## Résumé

Cette application utilise Servlets/JSP (MVC), JPA/Hibernate et PostgreSQL.
Le packaging Maven produit un WAR (`donate-blood.war`).

## Prérequis

- Java 17
- Maven 3.6+
- PostgreSQL (ou adapter `persistence.xml` pour un autre SGBD)
- Un conteneur Servlet/JSP (ex : Apache Tomcat 9/10)

## Structure importante

- Sources Java : `src/main/java/com/bloodbank/...`
- Vues JSP : `src/main/webapp/WEB-INF/views/` (ex. `donneurs.jsp`, `receveurs.jsp`, `association.jsp`)
- Configuration JPA : `src/main/resources/META-INF/persistence.xml`
- Unité de persistence : `blood` (déclarée dans `persistence.xml`)

## Build

Pour compiler le projet (sans exécuter les tests) :

```powershell
cd 'C:\Users\youco\Desktop\DonateBlood'
mvn -DskipTests=true compile
```

Pour générer le WAR :

```powershell
mvn -DskipTests=true package
# Le WAR se trouve dans target/donate-blood.war
```

## Déploiement

1. Déployer `target/donate-blood.war` dans le répertoire `webapps` d'Apache Tomcat.
2. Démarrer Tomcat et accéder à l'application (ex : http://localhost:8080/donate-blood/).

## Base de données

Le `persistence.xml` contient la configuration par défaut :

- URL : `jdbc:postgresql://localhost:5432/bloodbank`
- dialecte Hibernate : `PostgreSQLDialect`
- stratégie DDL Hibernate : `update` (mettra à jour le schéma automatiquement)

Exemples SQL (PostgreSQL) pour créer la base :

```powershell
# se connecter au serveur postgres avec un compte administrateur
# puis créer la base
# psql -U postgres
# CREATE DATABASE bloodbank;
# \q
```

## Configuration

Modifier `src/main/resources/META-INF/persistence.xml` pour changer les paramètres JDBC (URL / user / password) selon votre environnement.

## Usage rapide

- Gestion des donneurs : /donneurs
- Gestion des receveurs : /receveurs
- Association donneur ↔ receveur : /association

## Fonctionnalités notables

- Association Donneur↔Receveur avec règles de compatibilité sanguine (service `CompatibiliteService`).
- Relations JPA entre `Donneur` et `Receveur` (ManyToOne / OneToMany).
- Tri automatique des receveurs par priorité (CRITIQUE → URGENT → NORMAL).
- Vérifications métiers lors de l'association (disponibilité, compatibilité, nombre de poches requis).

## Notes de développement

- Java : sources ciblent Java 17 (voir `pom.xml`).
- Les DAOs utilisent chacun leur propre `EntityManager`/`EntityManagerFactory`. Pour des transactions atomiques multi-entity, envisager d'utiliser un `EntityManager` partagé ou gérer une transaction désormais.
- Les JSPs utilisent du scriptlet simple (projet pédagogique). Pour une application production, migrer vers JSTL/EL et MVC frameworks modernes.

## Dépannage

- Erreur de connexion DB : vérifier `persistence.xml` et que PostgreSQL écoute sur l'URL/port indiqués.
- Problèmes de compilation : exécuter `mvn -DskipTests=true compile` et corriger les erreurs affichées.

## Contribuer

PRs bienvenues. Pour toute modification touchant la base, merci d'indiquer les changements de schéma et de tester localement.

## Contact

Référentiel : ce workspace local (projet pédagogique).
