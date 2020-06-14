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
 * Entidad producto.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTOS")
public class Producto implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_PRODUCTO")
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
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String descripcion;

	/**
	 * Campo para saber si el producto está activo.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

	/**
	 * Entidad Cia.
	 */
	@JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
	@ManyToOne
	private Cia cia;

}
