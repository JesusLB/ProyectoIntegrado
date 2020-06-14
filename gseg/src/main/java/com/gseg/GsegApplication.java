package com.gseg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GsegApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GsegApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner initData(UsuarioService usuarioServicio, RolService rolService) {
//		return args ->{		
//			
//			Rol rolAdmin = new Rol();
//			Rol rolGestor = new Rol();
//			Rol rolUsuario = new Rol();
//			LocalDate fecha = LocalDate.now();
//					
////			//for(int i=4; i<= 100; i++) {
//			Usuario usuAdmin = new Usuario(
//					1, 
//					"00000000T", 
//					"00000000T", 
//					"ADMIN", 
//					"ADMIN", 
//					"ADMIN", 
//					fecha, 
//					null, 
//					true,
//					null);
//			rolAdmin = rolService.findById(1);
//			usuAdmin.setRol(rolAdmin);
//			usuarioServicio.saveUsuario(usuAdmin);
//////			//}
//////			
//////			for(int i=4; i<= 100; i++) {
//			Usuario usuGestor = new Usuario(
//					2, 
//					"00000001R", 
//					"00000001R", 
//					"GESTOR", 
//					"GESTOR", 
//					"GESTOR", 
//					fecha, 
//					null, 
//					true,
//					null);
//			rolGestor = rolService.findById(2);
//			usuGestor.setRol(rolGestor);
//			usuarioServicio.saveUsuario(usuGestor);
////			}
////			
//			Usuario usuUsuario = new Usuario(
//					3, 
//					"00000002W", 
//					"00000002W", 
//					"USUARIO", 
//					"USUARIO", 
//					"USUARIO", 
//					fecha, 
//					null, 
//					true,
//					null);
//			rolUsuario = rolService.findById(3);
//			usuUsuario.setRol(rolUsuario);			
//			usuarioServicio.saveUsuario(usuUsuario);
//			
//			
//		};
//	}	

	
}
