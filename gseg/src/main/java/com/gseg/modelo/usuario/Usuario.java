package com.gseg.modelo.usuario;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad usuario.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS")
public class Usuario implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_USUARIO")
	private long id;

	/**
	 * Usuario (NIF, NIE, CIF).
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "USUARIO")
	private String usuario;

	/**
	 * Contraseña.
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "CLAVE")
	private String clave;

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
	@Column(name = "APE2")
	private String ape2;

	/**
	 * Fecha de alta.
	 */
	@Column(name = "FECHA_ALTA")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaAlta;

	/**
	 * Fecha de baja.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_BAJA")
	private LocalDate fechaBaja;

	/**
	 * Campo para saber si el usuario está activo.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

	/**
	 * Entidad Rol.
	 */
	@NotNull(message = "{validacion.campo.obligatorio}")
	@JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
	@ManyToOne(fetch = FetchType.EAGER)
	private Rol rol;

}
