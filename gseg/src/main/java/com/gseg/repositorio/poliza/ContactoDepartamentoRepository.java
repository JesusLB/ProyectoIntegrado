package com.gseg.repositorio.poliza;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gseg.modelo.poliza.ContactoDepartamento;
import com.gseg.modelo.poliza.Departamento;

/**
 * Repositorio contacto de departamento.
 * 
 * @author Jesús López Barragán
 *
 */
public interface ContactoDepartamentoRepository extends JpaRepository<ContactoDepartamento, Long> {

	/**
	 * Localiza un contacto de departamento por su identificador.
	 * 
	 * @param id - Identificador de contacto departamento.
	 * @return Devuelve una entidad contacto departamento.
	 */
	ContactoDepartamento findById(long id);

	/**
	 * Obtiene una lista de contactos de departamento filtrada por departamento.
	 * 
	 * @param departamento - Entidad departamento.
	 * @return Devuelve una lista de entidades de contacto.
	 */
	List<ContactoDepartamento> findByDepartamento(Departamento departamento);

	/**
	 * Consulta nativa del identificador máximo de contactos de departamento.
	 * 
	 * @return Devuelve el identificaor máximo de todos los contactos de
	 *         departamento.
	 */
	@Query(value = "SELECT MAX(ID_CTO_DEPART) FROM P_CTO_DPTO", nativeQuery = true)
	Long findMaxId();

}
