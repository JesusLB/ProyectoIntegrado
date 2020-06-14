package com.gseg.modelo.poliza;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Ramo.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RAMOS")
public class Ramo {

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_RAMO")
	private long id;

	/**
	 * Nombre.
	 */
	@Column(name = "NOMBRE")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String nombre;

	/**
	 * Descripción.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;

	/**
	 * Campo para saber si el ramo está activo.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

}
