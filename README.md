# Threshold Challenge
Take home test to create a rest API that filters out users by a given threshold amount

## Prerequisites
- Java 8 installed and on the classpath
- A modern web browser
- Internet access

*NOTE: The following commands were tested on MacOS. You may need to modify them for your operating system.*

## Download and Compile
1. Download all files or clone this repository into a folder named _challange_
1. Open a terminal (cmd window) and change to that directory
1. Run the following command: ```./gradlew bootJar```

## Start the Service
1. Run the following command: ```java -jar ./build/libs/threshold-0.0.1-SNAPSHOT.jar &```
1. Open a browser tab or window to http://localhost:8443/successful_user?thresholdperc=50
1. Change the _thresholdperc_ value to see different results

## Stop the Service
1. Run the following command: ```ps -elf | grep jar```
1. Find the line that contains "java -jar" with a date about the time the application was stared
1. Note the process ID (PID) number
1. Run the following command: ```kill -9 <PID>``` repplacing <PID> with the process ID of the application
