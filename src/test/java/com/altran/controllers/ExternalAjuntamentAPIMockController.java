package com.altran.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ajuntament-test")
public class ExternalAjuntamentAPIMockController {

	@GetMapping(path="/mock", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAjuntamentMock() throws IOException {
		final InputStream is = this.getClass().getClassLoader().getResourceAsStream("ajuntament.json");
		final StringBuilder stringBuilder = new StringBuilder();
		String line = null;

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(is, StandardCharsets.UTF_8.name()))) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}

		return stringBuilder.toString();
	}

}
