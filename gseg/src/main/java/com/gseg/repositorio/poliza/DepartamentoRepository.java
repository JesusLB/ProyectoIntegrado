package com.gseg.repositorio.poliza;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.Departamento;

/**
 * Repositorio de departamento.
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

	/**
	 * Localiza un departamento por su identificador.
	 * 
	 * @param id - Identificador de departamento.
	 * @return Devuelve una entidad departamento.
	 */
	Departamento findById(long id);

	/**
	 * Obtiene una lista de departamentos filtrada por compañía.
	 * 
	 * @param cia - Entidad cia.
	 * @return Devuelve una lista de entidades departamentos.
	 */
	List<Departamento> findByCia(Cia cia);

	/**
	 * Consulta nativa del identificador máximo de departamentos.
	 * 
	 * @return Devuelve el identificaor máximo de todos los departamentos.
	 */
	@Query(value = "SELECT MAX(ID_DEPART) FROM DEPARTAMENTOS", nativeQuery = true)
	Long findMaxId();

}
