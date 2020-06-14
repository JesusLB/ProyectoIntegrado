package com.gseg.modelo.siniestro;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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

import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.TipoVia;
import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Siniestro.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SINIESTROS")
public class Siniestro implements Serializable {

	/**
	 * Versión de la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_STRO")
	private long id;

	/**
	 * Fecha de siniestro.
	 */
	@Column(name = "FECHA_STRO")
	@NotNull(message = "{validacion.campo.obligatorio}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaStro;

	/**
	 * Fecha de comunicación.
	 */
	@Column(name = "FECHA_COMUN")
	@NotNull(message = "{validacion.campo.obligatorio}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaComun;

	/**
	 * Fecha de cierre.
	 */
	@Column(name = "FECHA_CIERRE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCierre;

	/**
	 * Versión del siniestro.
	 */
	@Column(name = "VERSION")
	private String version;

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
	 * Número de la planta
	 */
	@Column(name = "PLANTA")
	private Integer planta;

	/**
	 * Número de la puerta.
	 */
	@Column(name = "PUERTA")
	private String puerta;

	/**
	 * Código postal.
	 */
	@Column(name = "CP")
	private String cp;

	/**
	 * Daños.
	 */
	@Column(name = "DANIOS")
	private String danios;

	/**
	 * Atestado.
	 */
	@Column(name = "ATESTADO")
	private String atestado;

	/**
	 * Estado del siniestro.
	 */
	@Column(name = "ESTADO")
	@NotEmpty
	private String estado;

	/**
	 * Responsabilidad.
	 */
	@Column(name = "RESP", length = 1, nullable = false)
	@NotEmpty
	private String responsabilidad;

	/**
	 * Tipo de siniestro (Auto, No auto).
	 */
	@Column(name = "TIPO", length = 1, nullable = false)
	@NotEmpty
	private String tipo;

	/**
	 * Previsión.
	 */
	@Column(name = "PREVISION", nullable = false)
	@NotEmpty
	private String prevision;

	/**
	 * Valoración.
	 */
	@Column(name = "VALORACION", nullable = false)
	@NotEmpty
	private String valoracion;

	/**
	 * Referencia de la compañía.
	 */
	@Column(name = "REFERENCIA_CIA", length = 128, nullable = true)
	private Float referenciaCia;

	/**
	 * Campo para saber si el siniestro está activo.
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

	/**
	 * Entidad Cliente.
	 */
	@NotNull
	@JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
	@ManyToOne(fetch = FetchType.EAGER)
	private Cliente cliente;

	/**
	 * Entidad Poliza.
	 */
	@NotNull
	@JoinColumn(name = "ID_POLIZA", referencedColumnName = "ID_POLIZA")
	@ManyToOne(fetch = FetchType.EAGER)
	private Poliza poliza;

}
