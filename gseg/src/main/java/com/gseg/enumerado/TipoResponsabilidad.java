package com.gseg.enumerado;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumerado tipo de responsabilidad.
 * 
 * @author Jesús López Barragán
 *
 */
@Getter
public enum TipoResponsabilidad {

	CULPA("Culpa", "C"), RECLAMACION("Reclamación", "R"), INDETERMINADO("Indetermindado", "I");

	/**
	 * Texto del enumerado.
	 */
	private final String text;

	/**
	 * Valor del enumerado.
	 */
	private final String value;

	private TipoResponsabilidad(final String text, final String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * Obtiene un listado de tipo responsabilidad.
	 * 
	 * @return Devuelve un listado de enumerados del tipo de responsabilidad de
	 *         siniestro.
	 */
	public static List<TipoResponsabilidad> getListaTipoResponsabilidad() {
		final List<TipoResponsabilidad> lista = new ArrayList<>();
		lista.add(TipoResponsabilidad.CULPA);
		lista.add(TipoResponsabilidad.RECLAMACION);
		lista.add(TipoResponsabilidad.INDETERMINADO);
		return lista;
	}

}
