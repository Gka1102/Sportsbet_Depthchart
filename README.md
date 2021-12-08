# Sportsbet_Depthchart
#Sports Depth Chart

## Introduction
Depth charts are utilized in all major sports, and they are extremely useful tools for coaches and players alike. This application helps manage list of sports, players & their positions with depth for each sport. 

Usecases covered:
* Add player to the sports depth chart.
* Remove player from the depth chart for a position.
* Print all depthchart positions.
* For a given player find all players below on the depthchart.

##Prerequisites
* java >= 1.8
* maven 3.8.1

##Requirements
* Implemented and tested using Java 11
* Spring Boot(version 2.6.1) with Tomcat 9 embedded
* Tests require JUnit and Mockito
* Project dependencies and compiling managed by Maven

## Run
* You can build the project and run the tests by running ```mvn clean install```
* This application is packaged as a jar.
* Other option: Run from eclipse as a spring boot application.
Once run(from IDE) you should see something similar to this:

		[INFO] Installing C:\Users\reyan\Desktop\Keerthi\Sportsbet_workspace\depthchart\target\depthchart-0.0.1.jar to C:\Users\reyan\.m2\repository\com\sports\depthchart\0.0.1\depthchart-0.0.1.jar
		[INFO] Installing C:\Users\reyan\Desktop\Keerthi\Sportsbet_workspace\depthchart\pom.xml to C:\Users\reyan\.m2\repository\com\sports\depthchart\0.0.1\depthchart-0.0.1.pom 
* Set configuration for SportsDepthchartApplication(main) class. 

### Database Details
* It uses an in-memory database (H2) to store the data. You can also use relational database like MySQL or PostgreSQL.
* To view and query the database you can browse to 

```http://localhost:8090/h2-console ```

	Username -> 'sa'
	Password  -> password 
	url -> 'jdbc:h2:mem:testdb' 
```
* Details can also be found in application.properties

# Assumptions
* Player name is unique with combination(if any) of first name & last name.
* After removing player from depth chart, depthPosition remains the same for existing players.

#### Table used :
sports_depth_chart : To save sports, players, positions & players position depth

### For adding new sport
 Add Sports & position details to SportsPositionReference class.
 
#Endpoints to call:

* UseCase 1 : Add player to the sports depth chart
```
	
	POST http://localhost:8080/depthchart/addPlayer	
	Note: Accept: application/json
	Content-Type: application/json



* UseCase 2 : Remove player from the depth chart for a position. 

```
DELETE http://localhost:8080/depthchart/remove/{playerName}/{position}
```

* UseCase 3 : Print all depthchart positions.
```
	
	GET http://localhost:8080/depthchart


* UseCase 4 : For a given player find all players below on the depthchart.

```
GET http://localhost:8080/depthchart/{playerName}/{position}
```

##Testing using Postman
* Refer to document Sports_depthchart.postman_collection.json 

##Junit
* Run SportsDepthchartApplicationTests as Junit test.
* Once run you should see something similar to this

		[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.437 s - in com.sports.depthchart.tests.SportsDepthchartApplicationTests
		



