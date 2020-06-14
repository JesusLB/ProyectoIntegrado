package com.gseg.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Provincia.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROVINCIAS")
public class Provincia implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_PROVINCIA")
	private long id;

	/**
	 * Nombre.
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "NOMBRE")
	private String nombre;

}
