package com.gseg.enumerado;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumerado de tipo de pago.
 * 
 * @author Jesús López Barragán
 *
 */
@Getter
public enum TipoPago {

	BANCO("Banco", "B"), FISICO("Fisico", "F");

	/**
	 * Texto del enumerado.
	 */
	private final String text;

	/**
	 * Valor del enumerado.
	 */
	private final String value;

	private TipoPago(final String text, final String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * Obtiene un listado del tipo de pago.
	 * 
	 * @return Devuelve un listado de enumerados con el tipo de pago.
	 */
	public static List<TipoPago> getListaTipoPago() {
		final List<TipoPago> lista = new ArrayList<>();
		lista.add(TipoPago.BANCO);
		lista.add(TipoPago.FISICO);
		return lista;
	}

}
