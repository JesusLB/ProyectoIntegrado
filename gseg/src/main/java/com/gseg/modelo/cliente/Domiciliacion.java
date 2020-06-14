package com.gseg.modelo.cliente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Domiciliacion.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOMICILIACIONES")
public class Domiciliacion implements Serializable {

	/**
	 * Verisón de la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_DOMICILIACION")
	private long id;

	/**
	 * Nombre.
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "NOMBRE")
	private String nombre;

	/**
	 * Primer apellido.
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "APE1")
	private String ape1;

	/**
	 * Segundo apellido.
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "APE2")
	private String ape2;

	/**
	 * Número de la cuenta bancaria.
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "NUMERO")
	private String numero;

	/**
	 * Campo para saber si la domiciliación está activa.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

	/**
	 * Entidad Cliente.
	 */
	@NotNull(message = "{validacion.campo.obligatorio}")
	@JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
	@ManyToOne
	private Cliente cliente;

}
