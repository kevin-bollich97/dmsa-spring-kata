package de.fhdo.kata.config;

import de.fhdo.kata.TestDataGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class for configuring the app.
 * <p>
 * ToDo: Activate component scan for the packages "dao" and "service".
 * <p>
 * ToDo: Declare a bean of type {@link TestDataGenerator} with the name
 * "dataGenerator" (please do not specify the name "dataGenerator" explicitly).
 */


@ComponentScan("de.fhdo.kata.dao")
@ComponentScan("de.fhdo.kata.service")
@Configuration
@ComponentScan
public class AppConfig {
    @Bean
    public TestDataGenerator dataGenerator(){
        return new TestDataGenerator();
    }
}
