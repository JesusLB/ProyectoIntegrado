package com.gseg.modelo.poliza;

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

import com.gseg.modelo.cliente.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad póliza.
 * 
 * @author Jesús López Barragán
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POLIZAS")
public class Poliza implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador.
	 */
	@Id
	@Column(name = "ID_POLIZA")
	private long id;

	/**
	 * Número de póliza.
	 */
	@Column(name = "NUM_POLIZA")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String numPoliza;

	/**
	 * Forma de pago (Bancario o físico).
	 */
	@Column(name = "FORMA_PAGO")
	@NotNull(message = "{validacion.campo.obligatorio}")
	private String formaPago;

	/**
	 * Fecha de efecto.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_EFECTO")
	@NotNull(message = "{validacion.campo.obligatorio}")
	private LocalDate fechaEfecto;

	/**
	 * Fecha de renovación.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_RENOV")
	@NotNull(message = "{validacion.campo.obligatorio}")
	private LocalDate fechaRenov;

	/**
	 * Fecha de vencimiento.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_VTO")
	@NotNull(message = "{validacion.campo.obligatorio}")
	private LocalDate fechaVto;

	/**
	 * Fecha de anulación.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_ANULACION")
	private LocalDate fechaAnulacion;

	/**
	 * Riesgo asegurado.
	 */
	@Column(name = "RIESGO")
	@NotEmpty(message = "{validacion.campo.obligatorio}")
	private String riesgo;

	/**
	 * Campo para saber si la póliza está activa.
	 */
	@Column(name = "ACTIVO")
	private Boolean activo;

	/**
	 * Entidad Ramo.
	 */
	@JoinColumn(name = "ID_RAMO", referencedColumnName = "ID_RAMO")
	@ManyToOne
	@NotNull(message = "{validacion.campo.obligatorio}")
	private Ramo ramo;

	/**
	 * Entidad Cia.
	 */
	@JoinColumn(name = "ID_CIA", referencedColumnName = "ID_CIA")
	@ManyToOne
	@NotNull(message = "{validacion.campo.obligatorio}")
	private Cia cia;

	/**
	 * Entidad Producto.
	 */
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
	@ManyToOne
	@NotNull(message = "{validacion.campo.obligatorio}")
	private Producto producto;

	/**
	 * Entidad Cliente.
	 */
	@JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(message = "{validacion.campo.obligatorio}")
	private Cliente cliente;

}
