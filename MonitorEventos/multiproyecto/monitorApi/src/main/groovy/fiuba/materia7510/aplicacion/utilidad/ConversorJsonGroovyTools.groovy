package fiuba.materia7510.aplicacion.utilidad

import groovy.json.* 

/***********************************************************/
/** INFORMACION UTIL SOBRE SINGLETON EN GRAILS
 * 
 * // Use @Singleton to create a valid singleton class.
 * // We can also use @Singleton(lazy=true) for a lazy loading.
 * // singleton class.
 * @Singleton
 class Util {
    int count(text) {
        text.size()
    }
}
 
assert 6 == Util.instance.count("mrhaki")
 
try {
    new Util()
} catch (e) {
    assert e instanceof RuntimeException
    assert "Can't instantiate singleton com.mrhaki.blog.Util. Use com.mrhaki.blog.Util.instance" == e.message
}
* 
 * */
@Singleton
class ConversorJsonGroovyTools {
	

  JsonSlurper obtenerInstanciaJsonSlurper(){
	new JsonSlurper()
  }
	/*****
	 *Nota personal:
	 *  As JsonSlurper is returning pure Groovy object instances,
	 * 	without any special JSON classes in the back, 
	 * 	its usage is transparent. 
	 * In fact, JsonSlurper results conform to GPath expressions. 
	 * GPath is a powerful expression language that is supported by multiple slurpers,
	 *  for different data formats (XmlSlurper for XML being one example).
	 * */
	 /*Retorna un objeto formato groovy.json.internal.LazyMap
	  * Ejemplo
	  * antes:		'''{"date":"Tue, 06 Oct 2015 09:10:52 GMT", "listado":[4,5,6,7.2]}'''  
	  * despues: 	[date:Tue, 06 Oct 2015 09:10:52 GMT, listado:[4, 5, 6, 7.2]]
	  * */
	
	def  parsear(def objetoJsonGroovyString ){
		obtenerInstanciaJsonSlurper()?.parseText(objetoJsonGroovyString)	
	}
	/***Nota personal: JsonOutput is responsible for serialising Groovy objects into JSON strings.
	 *  It can be seen as companion object to JsonSlurper,
	 *  being a JSON parser.
	 * 
	 * Ejemplo:def json = JsonOutput.toJson([name: 'John Doe', age: 42])
	 * 
	 * assert json == '{"name":"John Doe","age":42}'
	 * */
	def serializar_a_JsonString(def objetoJsonGroovy){
		JsonOutput.toJson(objetoJsonGroovy)		
	}
				
							
}
	

