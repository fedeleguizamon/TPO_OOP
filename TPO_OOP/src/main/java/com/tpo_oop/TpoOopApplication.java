package com.tpo_oop;

import com.tpo_oop.controllers.ExoPlanetController;
import com.tpo_oop.entities.Messages;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TpoOopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpoOopApplication.class, args);
		ExoPlanetController controller = new ExoPlanetController();
		List<String> mensajesSatelite1 = List.of("bienvenidos", "a", "", "orientada", "al", "");
		List<String> mensajesSatelite2 = List.of("", "bienvenidos", "a", "", "orientada", "al", "objeto");
		List<String> mensajesSatelite3 = List.of("", "", "programación", "", "", "");
		Messages messages = new Messages(mensajesSatelite1, mensajesSatelite2, mensajesSatelite3);
		System.out.println(controller.getEncryptedMessage(messages));
	}

}
