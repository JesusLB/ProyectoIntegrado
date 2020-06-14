package com.gseg.enumerado;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumerado del tipo de género.
 * 
 * @author Jesús López Barragán
 *
 */
@Getter
public enum TipoGenero {

	HOMBRE("Hombre", "H"), MUJER("Mujer", "M");

	/**
	 * Texto del enumerado.
	 */
	private final String text;

	/**
	 * Valor del enumerado.
	 */
	private final String value;

	private TipoGenero(final String text, final String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * Obtiene un listado de géneros.
	 * 
	 * @return Devuelve un listado de enumerados de género.
	 */
	public static List<TipoGenero> getListaGenero() {
		final List<TipoGenero> lista = new ArrayList<>();
		lista.add(TipoGenero.HOMBRE);
		lista.add(TipoGenero.MUJER);
		return lista;
	}

}
