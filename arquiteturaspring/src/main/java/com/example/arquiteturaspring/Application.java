package com.example.arquiteturaspring;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);

		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
		builder.bannerMode(Banner.Mode.OFF);
		builder.profiles("producão");

		builder.run(args);

		ConfigurableApplicationContext appContext = builder.context();
		//var produtoRepository = appContext.getBean("produtoRepository");


		var environment = appContext.getEnvironment();
		var appName = environment.getProperty("spring.application.name");
		var varPoc = environment.getProperty("app.config.variavel");
		System.out.println(appName);
		System.out.println(varPoc);


		var exemploValue = appContext.getBean(ExemploValue.class);
		exemploValue.imprimirVariavel();

		var appProperties = appContext.getBean(AppProperties.class);
		System.out.println(appProperties.toString());

	}

}
