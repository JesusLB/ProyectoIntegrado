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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Peritacion.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PERITACIONES")
public class Peritacion implements Serializable {

	/**
	 * Versión de la serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_PERITACION")
	private long id;

	/**
	 * Fecha de la peritación.
	 */
	@Column(name = "FECHA")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "{validacion.campo.obligatorio}")
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
	@ManyToOne
	private Siniestro siniestro;

	/**
	 * Entidad Perito.
	 */
	@JoinColumn(name = "ID_PERITO", referencedColumnName = "ID_PERITO")
	@ManyToOne(fetch = FetchType.EAGER)
	private Perito perito;

	/**
	 * Entidad Taller.
	 */
	@JoinColumn(name = "ID_TALLER", referencedColumnName = "ID_TALLER")
	@ManyToOne
	private Taller taller;

}
