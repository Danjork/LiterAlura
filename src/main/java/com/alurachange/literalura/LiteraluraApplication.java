package com.alurachange.literalura;

import com.alurachange.literalura.principal.Principal;
import com.alurachange.literalura.service.IAutor;
import com.alurachange.literalura.service.ILibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private ILibro libroRepository;

	@Autowired
	private IAutor autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraMenu();
	}
}
