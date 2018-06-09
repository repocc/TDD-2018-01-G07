package fiuba.materia7510.aplicacion.utilidad

import grails.gorm.transactions.Transactional
import fiuba.materia7510.aplicacion.generador.ClojureProcesador
import fiuba.materia7510.aplicacion.utilidad.ConversorJson


@Transactional
class InterceptadorService {

	/**
	 * Conversor de Json formato String  a Json formato Object.
	 * Retorna tipO JSONelement
	 * */
	
	static def parserService(def e){
		
		ConversorJson.parsear(e)
	}
}
