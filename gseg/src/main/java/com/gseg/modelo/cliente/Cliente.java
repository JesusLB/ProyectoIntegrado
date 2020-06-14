package com.gseg.modelo.cliente;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.TipoVia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Cliente.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENTES")
public class Cliente implements Serializable {

	/**
	 * Versión de la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_CLIENTE")
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
	 * Género.
	 */
	@Column(name = "GENERO")
	private String genero;

	/**
	 * Tipo de identificador (NIF, NIE, CIF).
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "TIPO_DOC")
	private String tipoDoc;

	/**
	 * Identificador del cliente.
	 */
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	@Column(name = "IDENTIFICADOR")
	private String identificador;

	/**
	 * Fecha de nacimiento.
	 */
	@Column(name = "FECHA_NAC")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaNac;

	/**
	 * Nombre de la vía.
	 */
	@Column(name = "NOMBRE_VIA")
	private String nombreVia;

	/**
	 * Número de la vía.
	 */
	@Min(0)
	@Column(name = "NUMERO")
	private Integer numero;

	/**
	 * Número de la planta.
	 */
	@Min(0)
	@Column(name = "PLANTA")
	private Integer planta;

	/**
	 * Letra de la puerta.
	 */
	@Column(name = "PUERTA")
	private String puerta;

	/**
	 * Número de código postal.
	 */
	@Column(name = "CP")
	private String cp;

	/**
	 * Teléfono.
	 */
	@Column(name = "TELEFONO")
	private String telefono;

	/**
	 * Móvil.
	 */
	@Column(name = "MOVIL")
	private String movil;

	/**
	 * Email.
	 */
	@Email
	@Column(name = "EMAIL")
	private String email;

	/**
	 * Campo para saber si el cliente está activo.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

	/**
	 * Entidad provincia.
	 */
	@JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_PROVINCIA")
	@ManyToOne(fetch = FetchType.EAGER)
	private Provincia provincia;

	/**
	 * Entidad municipio.
	 */
	@JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
	@ManyToOne(fetch = FetchType.EAGER)
	private Municipio municipio;

	/**
	 * Entidad tipo de vía.
	 */
	@JoinColumn(name = "ID_VIA", referencedColumnName = "ID_VIA")
	@ManyToOne(fetch = FetchType.EAGER)
	private TipoVia tipoVia;

}
