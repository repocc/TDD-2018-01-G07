package fiuba.materia7510.aplicacion.autenticacion

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

import fiuba.materia7510.aplicacion.usuario.Usuario
import fiuba.materia7510.aplicacion.autenticacion.RegistradorService
class AutenticadorController {

    RegistradorService registradorService
    AutenticadorService autenticadorService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autenticadorService.list(params), model:[autenticadorCount: autenticadorService.count()]
    }

    def show(Long id) {
        respond autenticadorService.get(id)
    }
	
	
   def iniciarSesion(){
	   
	   		
	   if (session.usuario_id != null){
		   String mensaje_sesion_iniciada="Ya a iniciado sesion."
	  
		   flash.mensaje = mensaje_sesion_iniciada + "usuario: ${session.usuario_id}"
		   
		   
		}else if(!params.nombreID && !params.clave){
			 
			 String mensaje	="Bienvenido"
	  
			 flash.mensaje 	= mensaje
			 
			 
		}else{
				
				
				Usuario usuario = registradorService.buscarUsuario(params.nombreId, params.clave)
				
				
				if (usuario == null){
					String mensaje_error_login="No hay nadie registrado con esos datos. Reingrese. "
					
					flash.mensaje = mensaje_error_login
				
				}else{
					 String mensaje_sesion_iniciada_correctamente = "Usuario autenticado. Inicio sesión correcto. Bienvenido:"
	   
					flash.mensaje = mensaje_sesion_iniciada_correctamente + "${usuario?.toString()}"
					
					session.usuario_id = usuario.autenticacionId
					session.usuario_nombre = usuario.toString() 
					registradorService.registrar(new Autenticador(nombre:"INICIO",usuario:usuario))	
				
				}
		   
		}
		
			
		render view:'iniciarSesion'
	 }
   
   
   
   def cerrarSesion(){
	   
	   String mensaje_sesion_no_iniciada = "No hay sesion iniciada."
	   
	   String mensaje_sesion_finalizada = "Sesion Finalizada."
	   
	   if(session.usuario_id == null){
		   
		   flash.mensaje = mensaje_sesion_no_iniciada
		
		}else{
			Usuario usuario 		= Usuario.findByAutenticacionId(session.usuario_id)
			session.usuario_id 		= null
			session.usuario_nombre 	= null
			flash.mensaje = mensaje_sesion_finalizada
			registradorService.registrar(new Autenticador(nombre:"CIERRE",usuario:usuario))	
			
		}
	   redirect action:'iniciarSesion'
	 }

    
}
