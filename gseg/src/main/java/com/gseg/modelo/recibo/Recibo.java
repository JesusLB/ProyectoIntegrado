package com.gseg.modelo.recibo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.gseg.modelo.poliza.Poliza;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Recibo.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RECIBOS")
public class Recibo implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_RECIBO")
	private long id;

	/**
	 * Fecha de efecto.
	 */
	@Column(name = "FECHA_EFECTO")
	@NotNull(message = "{validacion.campo.obligatorio}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaEfecto;

	/**
	 * Fecha de vencimiento.
	 */
	@Column(name = "FECHA_VTO")
	@NotNull(message = "{validacion.campo.obligatorio}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaVto;

	/**
	 * Prima neta.
	 */
	@Column(name = "PRIMA_NETA")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String primaNeta;

	/**
	 * Impuestos sobre primas de seguros.
	 */
	@Column(name = "IPS")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String ips;

	/**
	 * Consorcio.
	 */
	@Column(name = "CONSORCIO")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String consorcio;

	/**
	 * Impuestos.
	 */
	@Column(name = "IMPUESTOS")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String impuestos;

	/**
	 * Comisión.
	 */
	@Column(name = "COMISION")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String comision;

	/**
	 * Estado.
	 */
	@Column(name = "ESTADO")
	@NotNull(message = "{validacion.campo.obligatorio}")
	private String estado;

	/**
	 * Campo para saber si el recibo está activo.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

	/**
	 * Entidad Poliza.
	 */
	@ManyToOne
	@JoinColumn(name = "ID_POLIZA", referencedColumnName = "ID_POLIZA")
	private Poliza poliza;

}
