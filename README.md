# Mancala Game

Mancala Gameboard is a software that implements an ancient board game called Mancala or Kalah.
It is a web application that runs the game of 6-stone Mancala.
This is a Java based project using Maven for building and managing.

# Tech stack
1. `Java 11`
2. `Spring boot`
3. `MongoDB`
4. `Maven`
5. `Jquery`
6. `Bootstrap`
7. `Docker`
8. `Docker-Compose`
9. `Swagger`
10. `Junit` and `Mockito`

# Project's structure

- src/main/java - Server side java + spring based implementation
- src/test/java - Server side java + spring based Junit test case
- src/main/resources - Front End UI: css, js
 
 # How to run
 
 # 1.Build 
 The project needs MongoDB to persisting the game information. you can build and run it into any Development Idea
 if you have a running MongoDB on your OS. 
 Otherwise, this is a step-by-step guide on how to build and run the Mancala game using Docker-compose:
 
 - Start a terminal, then run:
 - mvn clean install (be sure JAVA_HOME is set to a Java 11 version home directory)
  (This will take a while to download the dependencies and 
 build mancala-game jar file. It will successfully build mancala-0.0.1-SNAPSHOT.jar file in target directory)
 - docker-compose -f docker-compose.yml build
 - docker-compose -f docker-compose.yml up -d

 # 2.Run 
 - open http://localhost:8080 page and play the game
 
 
 
 
 
 

