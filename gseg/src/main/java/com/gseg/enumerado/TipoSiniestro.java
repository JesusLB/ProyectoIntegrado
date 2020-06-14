package com.gseg.enumerado;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumerado tipo de siniestro.
 * 
 * @author Jesús López Barragán
 *
 */
@Getter
public enum TipoSiniestro {

	AUTO("Auto", "A"), NO_AUTO("No auto", "N");

	/**
	 * Texto del enumerado.
	 */
	private final String text;

	/**
	 * Valor del enumerado.
	 */
	private final String value;

	private TipoSiniestro(final String text, final String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * Obtiene un listado de tipo de siniestro.
	 * 
	 * @return Devuelve un listado de enumerados de tipo de siniestro.
	 */
	public static List<TipoSiniestro> getListaTipoSiniestro() {
		final List<TipoSiniestro> lista = new ArrayList<>();
		lista.add(TipoSiniestro.AUTO);
		lista.add(TipoSiniestro.NO_AUTO);
		return lista;
	}

}
