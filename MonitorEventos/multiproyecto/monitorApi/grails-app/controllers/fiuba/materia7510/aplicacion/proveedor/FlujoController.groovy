package fiuba.materia7510.aplicacion.proveedor

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import fiuba.materia7510.aplicacion.proveedor.Estado
import fiuba.materia7510.aplicacion.utilidad.Impresor

class FlujoController {

    FlujoService flujoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond flujoService.list(params), model:[flujoCount: flujoService.count()]
    }

    def show(Long id) {
        respond flujoService.get(id)
    }

    def create() {
        //respond new Flujo(params)
        [flujo: new Flujo(params), estado: Estado.list() ]
    }

    def save(Flujo flujo) {
        if (flujo == null) {
            notFound()
            return
        }

        try {
            flujoService.save(flujo)
        } catch (ValidationException e) {
            respond flujo.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'flujo.label', default: 'Flujo'), flujo.id])
                redirect flujo
            }
            '*' { respond flujo, [status: CREATED] }
        }
    }

    def edit(Long id) {
       // respond flujoService.get(id)
       [flujo: flujoService.get(id), estados: Estado.list() ]
    }
    def actualizarFlujo(){
		
		Impresor.instance.consola( "[ActualizandoFlujo]...[parametros:${params}]",true)
		
		def encontrado_estado 	= null
		def encontrado_flujo	= null
		encontrado_estado 		= Estado.get(params.estado) //params retorna el id 
		encontrado_flujo		= Flujo.get(params.id_flujo)
		def agregado			= null
		agregado = (encontrado_estado && encontrado_flujo)?encontrado_flujo?.addToEstados(encontrado_estado):null
		 
		 agregado?update(encontrado_flujo):update(null)
			
		
		
		}
	def agregarEstado(Flujo flujo, Estado estado){
		
		Impresor.instance.consola( "Derivando [agregarEstadoVista]...[parametros: ${params}]",true)
		render view:'agregarEstadoVista', model:[flujo:flujo,estados:Estado.list()]
		
		
		
	}
    def update(Flujo flujo) {
        if (flujo == null) {
            notFound()
            return
        }
		
		println "Estoy en update:*******************"
		println flujo.dump()
		
        try {
            flujoService.save(flujo)
        } catch (ValidationException e) {
            respond flujo.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'flujo.label', default: 'Flujo'), flujo.id])
                redirect flujo
            }
            '*'{ respond flujo, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        flujoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'flujo.label', default: 'Flujo'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'flujo.label', default: 'Flujo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
