package com.gseg.enumerado;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumerado Tipo de estado del recibo.
 * 
 * @author Jesús López Barragán
 *
 */
@Getter
public enum TipoEstadoRecibo {

	COBRADO("Cobrado", "C"), FACTURADO("Facturado", "F"), DEVUELTO("Devuelto", "D"), ANULADO("Anulado", "A");

	/**
	 * Texto del enumerado.
	 */
	private final String text;

	/**
	 * Valor del enumerado.
	 */
	private final String value;

	private TipoEstadoRecibo(final String text, final String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * Obtiene un listado con el tipo de estado del recibo.
	 * 
	 * @return Devuelve un listado de enumerados del tipo de estado del recibo.
	 */
	public static List<TipoEstadoRecibo> getListaTipoEstadoRecibo() {
		final List<TipoEstadoRecibo> lista = new ArrayList<>();
		lista.add(TipoEstadoRecibo.COBRADO);
		lista.add(TipoEstadoRecibo.FACTURADO);
		lista.add(TipoEstadoRecibo.DEVUELTO);
		lista.add(TipoEstadoRecibo.ANULADO);
		return lista;
	}

}
