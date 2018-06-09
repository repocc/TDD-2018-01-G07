package fiuba.materia7510.aplicacion.utilidad

import grails.converters.JSON

/*Lo defino como clase abstraca*/
abstract class JsonMarshallers {

	// call this in BootStrap init(java.lang.Class<?> entidadLibro, groovy.lang.Closure<?> entidadLibroMarshaller)
	static init(java.lang.Class<?> entidad, groovy.lang.Closure<?> entidadMarshaller) {
		
		JSON.registerObjectMarshaller(entidad, entidadMarshaller)
		
	}

	// marshaller en este caso de aplicacion es un closure,
	 //aplicando a un dominio obteniendo un map.
	
	static entidadMarshaller = { java.lang.Class<?> entidad ->
		return [
				/*Completar, heredar */
		]
		/**
		 * Caso modelo para bookMarshaller:
		 * 
		 * Ejemplo de un libro completado los parametros aunque cabe la posibilidad de usar tambien
		 * 
		 * book = new Book(JSON.parse(yourJson)) PERO ATENCION QUE HAY PROBLEMAS CON FECHAS
		 * 
		 * 
		 * NOTA:"I have problems with this when Data types are involved 
		 * ... since in the JSON they appear as e.g. "2020-11-11"
		 *  and i get as null in the domain class aftar parsing."
		 * 
		 * 	
			isbn: book.isbn,
			title: book.title,
			author: "${book.author?.lastname}, ${book.author?.firstname}",
			year: book.releaseDate?.format("yyyy"),
			publisher: book.publisher?.name,
			pages: book.pageCount
		
		*/
		
	}
	
}

