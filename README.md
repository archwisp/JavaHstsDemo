# Java HSTS Demo 

The purpose of this project was to learn how to implement HTTP Strict
Transport Security with Spring MVC, Spring Boot, and Spring Security for
Java. 

## Summary

I am not a Java programmer, so there were three main steps I had to
accomplish when setting up this project:

1. Initialize a working Spring MVC application
2. Implement the Spring Security Web configuration
3. Implement HTTPS for the Spring Boot Tomcat server (The HSTS headers will not be sent over an HTTP connection)

### Project files

These are the files that make this demo work.

* **tomcat.pem**: The CA certificate for use by the client (Curl, in this case).
* **keystore.p12**: The keystore for the application.
* **src/main/resources/templates/greeting.html**: The view script for the
  Greeting controller.
* **src/main/java/com/securityps/demo/hsts/Application.java**: The main
  application class that executes the Spring application and embeds an
  HTTPS servlet in the Tomcat server that is started up by Spring Boot.
* **src/main/java/com/securityps/demo/hsts/WebSecurityConfig.java**: The Spring
  Security configuration file that tells the application to send the HSTS
  header. This is where you would configure other Spring security options
  as well.
* **src/main/java/com/securityps/demo/hsts/GreetingController.java**: A
  basic Greeting controller from the Spring MVC tutorial.
* **README.md**: This file.
* **pom.xml**: The Maven build script.

See the **References & Notes** section below for more details.

## Dependencies

To build this project, you will need the following:

* A working Java JRE installation
  * I used the Debian java-6-openjdk-i386 package (The default in Kali 1.0.7)
* A working Apache Maven installation
  * I used version 3.2.1 
  * Download a binary distribution from http://maven.apache.org/download.cgi

## Build & Run

1. Build the project:

        JAVA_HOME=/usr; ~/Tools/apache-maven-3.2.1/bin/mvn clean install

2. Run the project after it has been built:

        java -jar target/java-hsts-demo-0.1.0.jar

3. Access the application:

If you get an error when you run this, see the **Platform Bugs** section
below.

        curl --cacert tomcat.pem -i https://127.0.0.1:8443/greeting

## References & Notes

I used the guides in the list below to build this project. Keep in mind
that most of them neglect to include the required imports. I'm guessing
this is due to the use of an IDE that does this automatically? Either way,
make sure you pay attention to the imports in the source files.

* http://spring.io/guides/gs/serving-web-content/
* http://docs.spring.io/spring-security/site/docs/3.2.x/guides/helloworld.html
* http://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/headers.html#headers-hsts
  * There is an error in the HSTS example; I submitted an issue on Github
    that was moved to the JIRA tracker by Rob.
  * Neglects to mention a required import
* http://thoughtfulsoftware.wordpress.com/2014/01/05/setting-up-https-for-spring-boot/
  * Assumes you're using Java 8 with Lambda support
  * Again, doesn't tell about required imports
* http://docs.spring.io/spring-boot/docs/1.1.3.RELEASE/reference/htmlsingle/#howto-terminate-ssl-in-tomcat
  * More missing imports

### Platform Bugs

**TO GET HTTPS CONNECTIONS TO WORK** on Kali, I had to comment out the
following line in **/usr/lib/jvm/java-6-openjdk-i386/jre/lib/security/java.security**: 
 
        # security.provider.9=sun.security.pkcs11.SunPKCS11 ${java.home}/lib/security/nss.cfg

Make sure you're editing the file that is associated with the JRE that is
set to default on your system by running. I my case, I did this by
running:

        update-alternatives --display java
