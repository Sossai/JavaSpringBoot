package com.example.libraryapi;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;


@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Application.class, args);

		//var autorRepository = context.getBean(AutorRepository.class);
		//var autor = exemploSalvar(autorRepository);
		//System.out.println("Start App");
		//System.out.println(autor.toString());
	}
/*
	public static Autor exemploSalvar(AutorRepository autorRepository){
		Autor autor = new Autor();
		autor.setNome("José");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1960,01,26));

		return autorRepository.save(autor);
	}
*/
}
