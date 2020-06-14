package com.gseg.modelo.poliza;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Departamento.
 * @author Jesús López Barragán
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DEPARTAMENTOS")
public class Departamento implements Serializable {
	
	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_DEPART")
	private long id;
	
	/**
	 * Nombre.
	 */
	@Column(name = "NOMBRE")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String nombre;
	
	/**
	 * Teléfono.
	 */
	@Column(name = "TELEFONO")
	private String telefono;
	
	/**
	 * Descripción.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	/**
	 * Campo para saber si el departamento está activo.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;
	
	/**
	 * Entidad cia.
	 */
	@JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
	@ManyToOne
	private Cia cia;

}
