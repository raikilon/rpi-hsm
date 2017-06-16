# RPiHSM-CommandLine
This application receives the user commands, then it check the command syntax and others possibles errors (by communicating with the RPiHSM-IoT appication). If no errors occur it send to the RPiHSM-IoT application the right command.  

All the information and reports (JavaDocs, JaCoCo Test, Surefire report, JDepend) of the project can be found in the *site* directory after the execution of `mvn clean package site`  on the home directory of the project.
# Licenses
All dependencies used in this project come with a valid license.

1. [JCommander](http://jcommander.org/): Apache 2.0 License


# Prerequisites
These are the software prerequisites to use the RPiHSM-CommandLine application.

1. [Java Development Kit 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)

2. [Apache Maven 3.3.9](https://maven.apache.org/)

3. RPiHSM-API

# Configuration
To use this application this command `mvn clean package` must be executed in the home directory of the project.
