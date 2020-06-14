package com.gseg.modelo.poliza;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.TipoVia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Cia.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CIAS")
public class Cia implements Serializable {

	/**
	 * Versión de la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_CIA")
	private long id;

	/**
	 * Nombre.
	 */
	@Column(name = "NOMBRE")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String nombre;

	/**
	 * Tipo de identificador (NIF, NIE, CIF).
	 */
	@Column(name = "TIPO_DOC")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String tipoDoc;

	/**
	 * Identificador de cia.
	 */
	@Column(name = "IDENTIFICADOR")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String identificador;

	/**
	 * Nombre de la vía.
	 */
	@Column(name = "NOMBRE_VIA")
	private String nombreVia;

	/**
	 * Número de la vía.
	 */
	@Column(name = "NUMERO")
	private Integer numero;

	/**
	 * Número de planta.
	 */
	@Column(name = "PLANTA")
	private Integer planta;

	/**
	 * Letra de la puerta.
	 */
	@Column(name = "PUERTA")
	private String puerta;

	/**
	 * Código posta.
	 */
	@Column(name = "CP")
	private String cp;

	/**
	 * Teléfono.
	 */
	@Column(name = "TELEFONO")
	private String telefono;

	/**
	 * Email.
	 */
	@Column(name = "EMAIL")
	@Email
	private String email;

	/**
	 * Campo para saber si la compañía está activa.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

	/**
	 * Entidad Provincia.
	 */
	@JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_PROVINCIA")
	@ManyToOne(fetch = FetchType.EAGER)
	private Provincia provincia;

	/**
	 * Entidad Municipio.
	 */
	@JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
	@ManyToOne(fetch = FetchType.EAGER)
	private Municipio municipio;

	/**
	 * Entidad TipoVia.
	 */
	@JoinColumn(name = "ID_VIA", referencedColumnName = "ID_VIA")
	@ManyToOne(fetch = FetchType.EAGER)
	private TipoVia tipoVia;

}
