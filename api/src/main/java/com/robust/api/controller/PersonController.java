package com.robust.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robust.api.data.dto.PersonDto;
import com.robust.api.data.dto.PersonInsertDto;
import com.robust.api.service.PersonService;
import com.robust.api.utils.MediaType;


@RestController
@RequestMapping(value = "/api/v1/person")
public class PersonController {

	private final String ID = "id";
	
	@Autowired
	private PersonService service;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON,
							 MediaType.APPLICATION_XML,
							 MediaType.APPLICATION_YML })
	public ResponseEntity<List<PersonDto>> findAll() {
		List<PersonDto> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}", 
			    produces = { MediaType.APPLICATION_JSON, 
			    			 MediaType.APPLICATION_XML,
			    			 MediaType.APPLICATION_YML })
	public ResponseEntity<PersonDto> findById(@PathVariable(ID) Long id) {
		PersonDto obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, 
							  MediaType.APPLICATION_XML,
							  MediaType.APPLICATION_YML },
				 produces = { MediaType.APPLICATION_JSON, 
						 	  MediaType.APPLICATION_XML,
						 	  MediaType.APPLICATION_YML })
	public ResponseEntity<PersonDto> insert(@RequestBody PersonInsertDto obj) {
		PersonDto person = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(person.getKey()).toUri();
		
		return ResponseEntity.created(uri).body(person);
	}
}
