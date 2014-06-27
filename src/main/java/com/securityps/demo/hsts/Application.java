package com.securityps.demo.hsts;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.*;
import org.springframework.boot.context.embedded.tomcat.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.apache.catalina.connector.Connector;
import org.springframework.util.ResourceUtils;
import java.io.FileNotFoundException;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        // Generate a keystore with a CA certificate:
        //
        // keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
        // > Enter "keystorepass" as the password or change the code below
        // > Enter "127.0.0.1" as the first and last name

        // List the keys in the keystore:
        //
        // keytool -list -v -keystore keystore.p12 -storetype pkcs12
        
        // Export the CA certificate to a PEM file for curl to use:
        //
        // keytool -keystore keystore.p12 -storetype PKCS12 -exportcert -rfc -alias tomcat -file tomcat.pem 

        // curl --cacert tomcat.pem -i https://127.0.0.1:8443/greeting 

        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

        factory.addConnectorCustomizers(new TomcatConnectorCustomizer () {
            @Override
            public void customize(Connector connector) {
                connector.setPort(8443);
                connector.setSecure(true);
                connector.setScheme("https");
                connector.setAttribute("keyAlias", "tomcat");
                connector.setAttribute("keystorePass", "keystorepass");
                connector.setAttribute("keystoreType", "PKCS12");
                
                try {
                    connector.setAttribute("keystoreFile",
                        ResourceUtils.getFile("keystore.p12").getAbsolutePath());
                } catch (FileNotFoundException e) {
                    throw new IllegalStateException("Cannot load keystore", e);
                }

                connector.setAttribute("clientAuth", "false");
                connector.setAttribute("sslProtocol", "TLS");
                connector.setAttribute("SSLEnabled", true);
            }
        });

        return factory; 
    }
}
