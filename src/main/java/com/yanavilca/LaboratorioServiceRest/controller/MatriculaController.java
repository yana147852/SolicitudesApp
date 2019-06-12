package com.yanavilca.LaboratorioServiceRest.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yanavilca.LaboratorioServiceRest.model.Matricula;
import com.yanavilca.LaboratorioServiceRest.services.MatriculaService;


@RestController
public class MatriculaController {
	
	private static final String FILEPATH = "/var/data/matricula-api/images";
	
	@Autowired
	private MatriculaService matriculaService;
	
	@GetMapping("/matricula")
	
	public List<Matricula> matriculas() {

		List<Matricula> matriculas = matriculaService.listar();

		return matriculas;
	}
	
	@GetMapping("/matricula/images/{filename:.+}")
	public ResponseEntity<Resource> files(@PathVariable String filename) throws Exception {

		Resource resource = new UrlResource(Paths.get(FILEPATH).resolve(filename).toUri());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(FILEPATH).resolve(filename)))
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength())).body(resource);
	}
	
}
