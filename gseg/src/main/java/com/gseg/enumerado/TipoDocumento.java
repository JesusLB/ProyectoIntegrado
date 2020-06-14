package com.gseg.enumerado;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Enumerado tipo de documento.
 * 
 * @author Jesús López Barragán
 *
 */
@Getter
public enum TipoDocumento {

	NIF("NIF", "N"), CIF("CIF", "C"), NIE("NIE", "I");

	/**
	 * Texto del enumerado.
	 */
	private final String text;

	/**
	 * Valor del enumerado.
	 */
	private final String value;

	private TipoDocumento(final String text, final String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * Obtiene un listado de los tipos de documentos.
	 * 
	 * @return Devuelve un listado de enumerado.
	 */
	public static List<TipoDocumento> getListaTipoDocumento() {
		final List<TipoDocumento> lista = new ArrayList<>();
		lista.add(TipoDocumento.NIF);
		lista.add(TipoDocumento.CIF);
		lista.add(TipoDocumento.NIE);
		return lista;
	}

}
