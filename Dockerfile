# Étape 1 : Construction de l'application avec Maven et JDK
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Définir le répertoire de travail
WORKDIR /build

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml /build/pom.xml
RUN mvn dependency:go-offline

# Copier le reste du code source et construire l'application
COPY src /build/src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution de l'application avec un JRE léger
FROM eclipse-temurin:21-jre-alpine AS runtime

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré lors de l'étape précédente
COPY --from=build /build/target/*.jar /app/app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8080

# Définir la commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
