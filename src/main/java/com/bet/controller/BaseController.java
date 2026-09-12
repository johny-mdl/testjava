package com.bet.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bet.domain.BaseDomain;
import com.bet.dto.BaseDTO;
import com.bet.services.BaseCRUDService;

@RequestMapping(value = "/")
public abstract class BaseController<ENTITY extends BaseDomain, DTO extends BaseDTO> {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ENTITY> find(@PathVariable Integer id) {
		ENTITY obj = getService().find(id);
		return ResponseEntity.ok().body(obj);
	}

//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<BaseDTO>> findAll() {
//		List<ENTITY> list = getService().findAll();
//		List<BaseDTO> listDto = list.stream().map(obj -> getDTO(obj)).collect(Collectors.toList());
//		return ResponseEntity.ok().body(listDto);
//	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ENTITY>> findAll() {
		List<ENTITY> list = getService().findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ENTITY>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<ENTITY> list = getService().findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody DTO objDto) {
		ENTITY obj = getService().fromDTO(objDto);
		obj = getService().insertOrUpdate(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		getService().delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody DTO objDto, @PathVariable Integer id) {
		ENTITY obj = getService().fromDTO(objDto);
		obj.setId(id);
		obj = getService().insertOrUpdate(obj);
		return ResponseEntity.noContent().build();
	}

	abstract BaseCRUDService<ENTITY, DTO> getService();

	abstract BaseDTO getDTO(ENTITY entity);

}
