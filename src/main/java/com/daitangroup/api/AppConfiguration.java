package com.daitangroup.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@Import({AppPersistenceContext.class})
@PropertySource("classpath:database.properties")
@ComponentScan("com.daitangroup.api")
public class AppConfiguration {
}
