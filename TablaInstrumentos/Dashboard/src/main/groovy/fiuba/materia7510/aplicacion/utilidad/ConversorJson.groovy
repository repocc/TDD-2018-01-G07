package fiuba.materia7510.aplicacion.utilidad

import grails.converters.JSON
import org.grails.web.json.JSONObject

class ConversorJson {

  
static JSONObject	parsear (java.io.Reader reader){
//Parses the given JSON String and returns either a JSONObject or a JSONArray

	parse(reader)
}
static JSONObject	parsear(java.lang.String cadena){
//Parses the given JSON String and returns either a JSONObject or a JSONArray

			parse (cadena)
}
static JSONObject	parsear(java.io.InputStream is, java.lang.String encoding){	
//Parses the given JSON and returns either a JSONObject or a JSONArray
			parse (is, encoding)
}
static java.lang.Object	parsear(javax.servlet.http.HttpServletRequest request){
//Parses the given request's InputStream and returns either a JSONObject or a JSONArray
			parse (request)
}
				
java.lang.String	toString(boolean prettyPrint){
//Performs the conversion and returns the resulting JSON as String
			toString (prettyPrint)		
}
  
}
/**
 * JSONElement es la interface.
 * A JSONObject is an unordered collection of name/value pairs.
 * Its external form is a string wrapped in curly braces 
 * with colons between the names and values,
 *  and commas between the values and names. 
 * 
 * 
 * The generic get() and opt() methods return an object,
 *  which you can cast or query for type. 
 * There are also typed get and opt methods
 *  that do type checking and type coersion for you.

The put methods adds values to an object. For example,

     myString = new JSONObject().put("JSON", "Hello, World!").toString();
produces the string {"JSON": "Hello, World"}.

 * /
/** MODELO 
class BookController {

	// e.g. GET '../rest/books/$bookId'
	def show = {
		// nothing special here, just render domain objects as usual
		render (Book.findById(params.int('bookId')) as JSON)
	}
	* 
	* import groovy.json.JsonBuilder  
	* import groovy.json.JsonSlurper  
	* import groovy.transform.ToStrin
	// Person object
        def person = new Person(firstName: "John", lastName: "Doe")
        // Json String
        def personJSON = new JsonBuilder(person).toPrettyString()
        // Json String to Map
        def personMap = new JsonSlurper().parseText(personJSON)
        // using Map to convert to Person object type
        def newPerson = new Person(personMap)

}
*/
