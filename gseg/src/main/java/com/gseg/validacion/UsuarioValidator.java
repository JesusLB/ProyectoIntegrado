package com.gseg.validacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.utils.ConstantesMensajes;
import com.gseg.utils.Identificador;

/**
 * Validación usuario.
 * 
 * @author Jesús López Barragán
 *
 */
@Component
public class UsuarioValidator implements Validator {

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService UsuarioService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Usuario.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Usuario usuario = (Usuario) target;

		// Validación identificador usuario
		if (!usuario.getUsuario().trim().equals("")) {
			if (usuario.getUsuario().length() == 9) {
				if (!Identificador.isValido(usuario.getUsuario())) {
					errors.rejectValue("usuario", ConstantesMensajes.COD_ERROR_USUARIO, ConstantesMensajes.MSG_IDENTIFICADOR_NO_VALIDO);
				}
				if (usuario.getId() == 0 && usuarioExiste(usuario.getUsuario())) {
					errors.rejectValue("usuario", ConstantesMensajes.COD_ERROR_USUARIO, ConstantesMensajes.MSG_USUARIO_EXISTE);
				}
			} else {
				errors.rejectValue("usuario", ConstantesMensajes.COD_ERROR_USUARIO, ConstantesMensajes.MSG_TAMANIO_IDENTIFICADOR);
			}
		}

		// Validación rol
		if (usuario.getRol() == null || usuario.getRol().equals("-1")) {
			errors.rejectValue("rol.id", ConstantesMensajes.COD_ERROR_ROL, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}
	}

	/**
	 * Comprueba si un usuario existe.
	 * 
	 * @param usuario - Identificador del usuario.
	 * @return Devuelve true si existe, false, en caso contrario.
	 */
	private boolean usuarioExiste(final String usuario) {
		return UsuarioService.findByUsuario(usuario) != null;
	}

}
