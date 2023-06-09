package com.ipartek.formacion.gestionformacion.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ipartek.formacion.gestionformacion.entidades.Curso;
import com.ipartek.formacion.gestionformacion.entidades.Materia;
import com.ipartek.formacion.gestionformacion.entidades.Profesor;
import com.ipartek.formacion.gestionformacion.repositorios.CursoRepository;
import com.ipartek.formacion.gestionformacion.repositorios.MateriaRepository;
import com.ipartek.formacion.gestionformacion.repositorios.ProfesorRepository;

@Service
public class CursoServiceImpl implements CursoService {
	@Autowired
	private CursoRepository repo;
	
	@Autowired
	private ProfesorRepository profesorRepo;
	
	@Autowired
	private MateriaRepository materiaRepo;
	
	@Override
	public Iterable<Curso> listado() {
		return repo.listadoCompleto();
	}

	@Override
	public Curso alta(Curso curso) {
		try {
			repo.save(curso);
		} catch (DataIntegrityViolationException e) {
			throw new ServiciosException("Identificador duplicado", e);
		}
		return curso;
	}

	@Override
	public Iterable<Profesor> listadoProfesores() {
		return profesorRepo.findAll();
	}

	@Override
	public Iterable<Materia> listadoMaterias() {
		return materiaRepo.findAll();
	}
}
