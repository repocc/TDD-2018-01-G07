package monitorapi

import grails.util.Environment

import fiuba.materia7510.aplicacion.utilidad.TesterBootstrap
class BootStrap {

    /**
		 * grails.util.Environment.current
		 * getCurrent()
		 * Permite conocer el ambiente de trabajo en
		 * todo momento:
		 * Por lo tanto en el inicio de la aplicacion,
		 * realizo un switch.
		 * 
		 * 
		 * */
    def init = { servletContext ->
    	
		 switch (Environment.current) {
            case Environment.DEVELOPMENT:
                configurarParaDesarrollo()
                break
            case Environment.PRODUCTION:
                configurarParaProduccion()
                break
        }      
    }
    
    def configurarParaDesarrollo(){
		
		TesterBootstrap.cargar_Datos()
	} 
	
    def configurarParaProduccion(){
		/**
		 * De momento para esta aplicacion, y por el tema de 
		 * habilitar/deshabilitar DatabaseConsole, 
		 * es indistinto configurar para uno u otro ambiente de desarrollo.
		 * */
		configurarParaDesarrollo()		
	}
	
    def destroy = {
		
    }
}

