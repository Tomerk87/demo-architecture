# README #

This README would normally document whatever steps are necessary to get your application up and running.

## What is this repository for? ##

This repository is a project with micro-services running using:
* ***Java*** Spring development
* ***Databases***: *MySql, MongoDb*
* ***Cache***: *Redis*
* ***Message Queue***: *Kafka*

## How to run ##

### Start docker databases ###
#### Commands: ####
##### Run commands from home directory: #####
* **Run databases**:  _docker-compose -f .\docker-compose\docker-compose.yml up -d_


### Connect to db using Dbeaver ###

* Connect to new db according to *docker-compose.yml* folder.

#### On Public Key Retrieval is not allowed error
* In *Connection Settings* -> _Driver properties_ set value to ***TRUE*** 

# Services #
## *Globe service* ##
Markup : * ***Spring Boot*** application.
* Connected to ***MySql***.
* Using ***Redis*** as *cache*.
* Cache eTags supported only on ***Continent CRUD*** operations.
* Controller end point returns an ***eTag*** header.
* If Request arrives with the *eTag* header, it will first try to collect from cache before going to MySql.

## *Gateway service* ##
* ***Spring Boot*** application.
* Sending ***Feign*** Rest APIs to Globe service and return responses.
* Saving ***eTag*** in memories to be sent as header for the calls.
* Connected to ***Kafka*** message queue as a ***Kafka Producer*** to publish messages.
  * Gets a positive response and publish a message to a topic.
  * The message contains an ***ID*** and the ***eTag*** for the ID.

## *Consumer service* ##
* ***Spring Boot*** application.
* Connected to ***MongoDb***.
* Connected to ***Kafka*** message queue as a ***Kafka Consumer***.
* Make changes in the **Globe Service** using ***Rest Template*** API calls:
    * Check the ID and eTag got from the response would also arrive from Kafka.
    * Once a change was made - sends an async call to ***Gateway Service*** to get a new ***eTag*** and check it.
