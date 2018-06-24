package fiuba.materia7510.aplicacion.autenticacion

import grails.gorm.transactions.Transactional
import fiuba.materia7510.aplicacion.usuario.Usuario
import fiuba.materia7510.aplicacion.autenticacion.Autenticador


@Transactional
class RegistradorService {

    def iniciarSesion(def loginId, def clave) {
		
		buscarUsuario(loginId, clave)

    }
    
    def buscarUsuario(String loginID, String clave){
		
		Usuario usuario_encontrado = Usuario.findByAutenticacionId(loginID)
			
		(usuario_encontrado && (usuario_encontrado?.password == clave))? usuario_encontrado:null		
	}
		
	def registrar(Autenticador autenticador){
		
		if (!autenticador?.hasErrors()){
			autenticador?.save(failOnError:true,flush:true)
		}
	} 	
}
