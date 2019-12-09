# How To Run
Step 1. Clone Respository

Step 2. Export Server and Client modules into to Executable Jars

Link to Eclipse instructions - [Export Project as Jar](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=3&cad=rja&uact=8&ved=2ahUKEwjH2I305M_lAhXSmq0KHX50DQMQFjACegQICxAH&url=https%3A%2F%2Fhelp.eclipse.org%2Fkepler%2Ftopic%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-37.htm&usg=AOvVaw0j3qyOaoLXHgagip1UASI-)
    
Step 3. Execute jar files

    type in terminal/Command Prompt:  java -jar file.jar

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
![dependency example](https://github.com/csucs414/cs414-f18-001-970DreamTeam/blob/master/documents/images/JUnit%20dependency%20example.png)

Modules
=====

This project uses two modules: client and server. Each should be exported and executed separatly. Server handles client to client communication, while client handles the GUI and game logic. The server IP address, located in the Server class should be changed to reflect the host that you are currently using.

Database
=====

The server relies on the use of a mysql database in order to store user information. The connection information for the database can be found under the DBHandler class's constructor. In order to use a custom database, the information in this constructor must be changed to reflect that of the owned database. The owned database must be a table that contains three fields: Name, Email, and Password. After this setup, the code will handle input and reads from the database. 
