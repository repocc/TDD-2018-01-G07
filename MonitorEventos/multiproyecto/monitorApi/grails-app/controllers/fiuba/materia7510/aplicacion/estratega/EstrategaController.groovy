package fiuba.materia7510.aplicacion.estratega

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

import grails.converters.JSON


import fiuba.materia7510.aplicacion.MotorService
class EstrategaController {

static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
		flash.message ="Aqui crear indice. Se retorna al inicio."
		redirect(uri:'/')		
	}
	def derivar_IniciarMonitor(){
		redirect (controller:"publicador", action:"derivar_Iniciar" )
	}
	def derivar_InicioGrails(){
		redirect (uri:'/')
	}
	def derivar_estrategia_1(){ 
		render (view: "renderEstrategia1") 
		}
	def derivar_estrategia_2(){ 
		render (view: "renderEstrategia2")  
		}
	
}


