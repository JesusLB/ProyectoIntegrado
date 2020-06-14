package com.gseg.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gseg.modelo.usuario.Rol;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.usuario.RolService;
import com.gseg.servicio.usuario.UsuarioService;

/**
 * Autentificación del usuario.
 * 
 * @author Jesús López Barragán
 *
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Servicio de rol.
	 */
	@Autowired
	RolService rolService;

	/**
	 * Encriptación de la contraseña.
	 */
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	/**
	 * Carga los datos de un usuario.
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final Usuario usuario = usuarioService.findByUsuario(username);
		UserBuilder builder = null;

		if (usuario != null) {
			final Rol rol = rolService.findById(usuario.getRol().getId());
			builder = User.withUsername(username);
			builder.disabled(!usuario.getActivo());
			builder.password(passwordEncoder.encode(usuario.getClave()));
			builder.authorities(new SimpleGrantedAuthority(rol.getNombre()));
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}

		return builder.build();

	}

}
