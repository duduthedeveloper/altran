package com.altran.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.domain.ajuntament.AjuntamentFilterDecorator;
import com.altran.domain.ajuntament.OrganizationVO;
import com.altran.infrastructure.ExternalQueryService;
import com.altran.infrastructure.exceptions.ExternalQueryException;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ajuntament")
public class AjuntamentController {

	private static Logger logger;

	@Autowired
	@Qualifier("ajuntamentQueryService")
	private ExternalQueryService<OrganizationVO> externalQueryService;

	@Autowired
	public AjuntamentController(final Logger logger) {
		AjuntamentController.logger = logger;
	}

	@GetMapping(path = "/search", params = "v=1")
	public Flux<OrganizationVO> searchOnAjuntament(final AjuntamentFilterDecorator filter) {
		AjuntamentController.logger.info("Search on ajuntament by the filters: {}", filter);
		return externalQueryService.searchOnExternalAPI(filter);
	}

	@ExceptionHandler(ExternalQueryException.class)
	public ResponseEntity<?> handleExternalQueryException(final ExternalQueryException ex) {
		return ResponseEntity.status(ex.getCode()).build();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleInternalServerError(final IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
