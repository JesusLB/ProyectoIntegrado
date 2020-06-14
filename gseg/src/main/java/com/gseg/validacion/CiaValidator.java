package com.gseg.validacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.poliza.Cia;
import com.gseg.servicio.poliza.CiaService;
import com.gseg.utils.ConstantesMensajes;
import com.gseg.utils.Identificador;

/**
 * Validación de cia.
 * 
 * @author Jesús López Barragán
 *
 */
@Component
public class CiaValidator implements Validator {

	/**
	 * Servicio de cia.
	 */
	@Autowired
	CiaService ciaService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Cia.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Cia cia = (Cia) target;

		// Validación del identificador
		if (!cia.getIdentificador().trim().equals("")) {
			if (cia.getIdentificador().length() == 9) {
				if (!Identificador.isValido(cia.getIdentificador())) {
					errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_IDENTIFICADOR_NO_VALIDO);
				}
				if (cia.getId() == 0 && ciaExiste(cia.getIdentificador())) {
					errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_CIA_EXISTE);
				}
			} else {
				errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_TAMANIO_IDENTIFICADOR);
			}
		}

		// Validación tipo de documento del identificador
		if (cia.getTipoDoc() == null || cia.getTipoDoc().equals("-1")) {
			errors.rejectValue("tipoDoc", ConstantesMensajes.COD_ERROR_TIPO_DOCUMENTO, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}
	}

	/**
	 * Comprueba si una cia existe.
	 * 
	 * @param identificador - Identificador.
	 * @return Devuelve true si existe, false, en caso contrario.
	 */
	private boolean ciaExiste(final String identificador) {
		return ciaService.findByIdentificador(identificador) != null;
	}

}
