package com.gseg.modelo.siniestro;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.TipoVia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Lesionado.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LESIONADOS")
public class Lesionado implements Serializable {

	/**
	 * Versión de la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_LESIONADO")
	private long id;

	/**
	 * Nombre.
	 */
	@Column(name = "NOMBRE")
	private String nombre;

	/**
	 * Primer apellido.
	 */
	@Column(name = "APE1")
	private String ape1;

	/**
	 * Segundo apellido.
	 */
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
	@Column(name = "TIPO_DOC")
	private String tipoDoc;

	/**
	 * Identificador del lesionado.
	 */
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
	@Column(name = "NUMERO")
	private Integer numero;

	/**
	 * Número de la planta.
	 */
	@Column(name = "PLANTA")
	private Integer planta;

	/**
	 * Letra de la puerta.
	 */
	@Column(name = "PUERTA")
	private String puerta;

	/**
	 * Código postal.
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
	@Column(name = "EMAIL")
	@Email
	private String email;

	/**
	 * Lesiones.
	 */
	@Column(name = "LESIONES")
	private String lesiones;

	/**
	 * Centro sanitario.
	 */
	@Column(name = "CENTRO_SANITARIO")
	private String centroSanitario;

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

	/**
	 * Entidad Siniestro.
	 */
	@JoinColumn(name = "ID_STRO", referencedColumnName = "ID_STRO")
	@ManyToOne()
	private Siniestro siniestro;

}
