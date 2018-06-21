package fiuba.materia7510.aplicacion.utilidad


@Singleton
class Impresor {

/**
 * Nota Personal: Conflicto con el uso de GString, de groovy.
 * Incompatibles:
 * --org.codehaus.groovy.runtime.GStringImpl
 * --java.lang.String
 * La recomendacion es a la fecha Junio-2018: "${blabla}".toString().
 * 
 * https://github.com/bmuschko/gradle-docker-plugin/issues/404
 * This is not a plugin issue as much as its a gradle/groovy issue. 
 * We can do some tricks (really just call toString on everything) on our end to minimize this,
 * but it's really gradle/groovy issue more than anything else.
 * */
 	
	def consola(def contenido,boolean estilizado=false){
		
		estilizado?estilizar_impresion(contenido):println (contenido)
				
	}
	private def estilizar_impresion(def contenido){
	
	
		println ()
		println "*************************************"
		println "*"
		
		println contenido
		
		println "*"
		println "*************************************"
		println ""
		
	}	 							
/*
	private def estilizar_impresion(org.codehaus.groovy.runtime.GStringImpl contenido){
	
	
		println ()
		println "*************************************"
		println "*"
		
		println contenido
		
		println "*"
		println "*************************************"
		println ()
		
	}
						
	private def estilizar_impresion(java.lang.String contenido){
	
	
	println ()
	println "*************************************"
	println "*"
	
	println contenido
	
	println "*"
	println "*************************************"
	println ()
	
	}	 			

*/
}//fin clase Impresor
	

