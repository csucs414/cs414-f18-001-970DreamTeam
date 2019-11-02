Maven
=====

Be sure you have the latest version of Maven installed. You can check what version of Maven 
you currently have installed by running “mvn --version” in the terminal. The current version 
of Maven is 3.6.2. If you do not have the latest version installed on your machine, you can 
go to the [Maven download page](https://maven.apache.org/download.cgi). 

JUnit 5
=====

JUnit 5 requires Java 8 (or higher) to run. Even though you can still test code that has been
compiled with previous versions of the JDK, it is recommended that you update your JDK to the
latest version in order to employ the full benefits of JUnit 5. Be sure to verify that the IDE
you are using is capable of using JUnit 5. Two IDEs that have JUnit 5 installed automatically are 
IntelliJ and Eclipse.

To set up JUnit 5 the process is very clear cut simply add the following dependency to your pom.xml:
![dependency example](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/images/JUnit%20dependency%20example.png)
