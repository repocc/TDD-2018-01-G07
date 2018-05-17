package fiuba.materia7510.aplicacion.publicador

//import grails.gorm.transactions.*
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.converters.JSON

//@Transactional(readOnly = true)

class PublicadorController  {
	//static defaultAction = "publicacion"

    /*
     * Alternativa a render
     * render(contentType: 'application/json'){
        [
           'status' : "ok"
        ]
		}
		* 
		* 
		* Using the built-in Grails JSON converter makes this easier

			import grails.converters.JSON

		class BookController {
		def save = {
		def book = new Book(JSON.parse(yourJson))
		book.save(flush:true)
		}
		}
		* 
		* More example usages:

json(1,2,3) == "[1,2,3]"
json { name "Bob" } == '{"name":"Bob"}'
json([1,2,3]) { n it } == '[{"n":1},{"n":2},{"n":3}]'
	****************************************/
	static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
       // respond publicadorService.list(params), model:[publicadorCount: publicadorService.count()]
    }

    def show(Long id) {
        //respond publicadorService.get(id)
    }

	def publicar() {
		def dato_json ='[{  value: 30, color:"#1abc9c"},{value : 50,color : "#2ecc71"},{value : 100,color : "#3498db"},{value : 40,color : "#9b59b6"}, {value : 120,color : "#34495e"}]';
		
		def dato_no_json = [status_temp: 25, status_pending: 25, status_partial: 25, status_complete: 25]
    
		def un_dato_json = [value: 15]
		
		//render(dato_json as JSON)
			
		respond un_dato_json
					
		}
	
 
 }
