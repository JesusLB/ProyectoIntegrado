package com.gseg.modelo.siniestro;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Actuación judicial.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACT_JUDICIALES")
public class ActJudicial implements Serializable {

	/**
	 * Versión de la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_JUICIO")
	private long id;

	/**
	 * Nombre.
	 */
	@Column(name = "NOMBRE")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String nombre;

	/**
	 * Fecha de actuación.
	 */
	@Column(name = "FECHA")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	/**
	 * Descripción.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;

	/**
	 * Entidad Siniestro.
	 */
	@JoinColumn(name = "ID_STRO", referencedColumnName = "ID_STRO")
	@ManyToOne()
	private Siniestro siniestro;

}
