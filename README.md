# README #

This README would normally document whatever steps are necessary to get your application up and running.

## What is this repository for? ##

This repository is a project with micro-services running using:
* Java Spring development
* Databases: *MySql, MongoDb*
* Cache: *Redis*

## How to run ##

### Start docker databases ###
#### Commands: ####
##### Run commands from home directory: #####
* **Run databases**:  _docker-compose -f .\docker-compose\docker-compose.yml up -d_


### Connect to db using Dbeaver ###

* Connect to new db according to *docker-compose.yml* folder.

#### On Public Key Retrieval is not allowed error
* In *Connection Settings* -> _Driver properties_ set value to ***TRUE*** 

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact