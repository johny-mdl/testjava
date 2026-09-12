package com.bet.services;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bet.domain.BaseDomain;
import com.bet.repository.BaseRepository;
import com.bet.services.exceptions.DataIntegrityException;
import com.bet.services.exceptions.ObjectNotFoundException;

@Service
public abstract class BaseCRUDService<ENTITY extends BaseDomain, DTO> {

	public abstract ENTITY fromDTO(@Valid DTO objDto);

	protected abstract BaseRepository<ENTITY> getRepo();

	public ENTITY find(Integer id) {
		Optional<ENTITY> obj = getRepo().findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + getEntityClass().getName()));
	}

	@Transactional
	public ENTITY insertOrUpdate(ENTITY obj) {
		obj = getRepo().save(obj);
		return obj;
	}

	public void delete(Integer id) {
		find(id);
		try {
			getRepo().deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public List<ENTITY> findAll() {
		return getRepo().findAll();
	}

	public Page<ENTITY> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return getRepo().findAll(pageRequest);
	}

	@SuppressWarnings("unchecked")
	private Class<ENTITY> getEntityClass() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return ((Class<ENTITY>) parameterizedType.getActualTypeArguments()[0]);
	}

}
