package com.gseg.enumerado;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumerado del estado de siniestro.
 * 
 * @author Jesús López Barragán
 *
 */
@Getter
public enum TipoEstadoSiniestro {

	TRAMITE("En trámite", "E"), JUICIO("Juicio", "J"), CERRADO("Cerrado", "C");

	/**
	 * Texto del enumerado.
	 */
	private final String text;

	/**
	 * Valor del enumerado.
	 */
	private final String value;

	private TipoEstadoSiniestro(final String text, final String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * Obtiene un listado con el tipo de estado del siniestro.
	 * 
	 * @return Devuelve un listado de enumerados del estado de siniestro.
	 */
	public static List<TipoEstadoSiniestro> getListaTipoEstadoSiniestro() {
		final List<TipoEstadoSiniestro> lista = new ArrayList<>();
		lista.add(TipoEstadoSiniestro.TRAMITE);
		lista.add(TipoEstadoSiniestro.JUICIO);
		lista.add(TipoEstadoSiniestro.CERRADO);
		return lista;
	}

}
