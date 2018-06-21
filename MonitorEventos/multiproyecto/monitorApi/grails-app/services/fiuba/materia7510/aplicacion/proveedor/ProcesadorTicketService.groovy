package fiuba.materia7510.aplicacion.proveedor

import grails.gorm.transactions.Transactional

import grails.converters.JSON

import groovy.json.* 
//import java.sql.Timestamp
//import java.time.Instant
import static java.util.Calendar.*

import fiuba.materia7510.aplicacion.utilidad.ConversorJsonGroovyTools
import fiuba.materia7510.aplicacion.utilidad.Impresor
import fiuba.materia7510.aplicacion.proveedor.Ticket
import fiuba.materia7510.aplicacion.proveedor.TicketMock
import fiuba.materia7510.aplicacion.generador.Estadio
import fiuba.materia7510.aplicacion.generador.Regla
import fiuba.materia7510.aplicacion.MotorService

@Transactional
class ProcesadorTicketService {

    /**
     * El método recibe:
     *  El dato-ticket.
     *  El nombre de la instancia persisitida de la clase Estadio,
     * que contienen la estructura a graficar. Este método definido de 
     * manera reutilizable para tests-desarrollo 
     * y para uso final de la aplicacion(producción).
     *  El nombre de la instancia persistida de la clase Estadio,
     * que contiene el estado-State-Status,
     * retornado por el procesamiento de reglas, del motor-API CLOJURE-
     * */
	def procesar(def ticket, String plantilla_json, String nombre_estadio_motor){
		
		
		Impresor.instance.consola( "[PROCESAR].[TICKET].[Motor-APIClojure]:\n  ${ticket}",true)
		
		//para tiempo de procesamiento por ticket
		def inicio_milisegundos 	= new Date().getTime()
		 
		//para la estructura Json a enviar al consumidor final
		Estadio estadio_graficable 	= Estadio.findByNombre(plantilla_json)
		
		String  patron_JsonString 	= estadio_graficable.estadio 
		
		def ticket_procesado_map 	= aplicarMotorApiClojure(ticket,nombre_estadio_motor)
		
		
		//Impresor.instance.consola( "Plantilla de estructrura Json extensible",true)
		//Impresor.instance.consola( estadio_graficable.estadio, true)
		Impresor.instance.consola( "[PROCESADO].[TICKET].[Motor-APIClojure]:\n ${ticket_procesado_map}",true)
		
		
		def datos_map = preparar_Estructura_para_graficar(ticket_procesado_map)
		
		Impresor.instance.consola( "[PROCESADO].[TICKET].[REESTRUCTURADO]:\n ${datos_map}",true)
		
		/**Definiendo y configurando estructura JSON**/
		
		
		groovy.json.internal.LazyMap patron_jsonObject = ConversorJsonGroovyTools.instance.parsear(patron_JsonString)
		
		/**Agregando datos de ticket procesado **/
		
		patron_jsonObject.contadores.claves 	= datos_map.contadores.claves
		
		patron_jsonObject.contadores.valores 	= datos_map.contadores.valores
		
		patron_jsonObject.sennales.claves 		= datos_map.sennales.claves
		
		patron_jsonObject.sennales.valores 		= datos_map.sennales.valores		
		
		/** Fin agregado datos de ticket procesado **/
		
		String fecha_dato = ticket.lastUpdated.toInstant().toString()
		
		groovy.json.internal.LazyMap patron_JsonObject_actualizado = configurar_Json(patron_jsonObject, fecha_dato , inicio_milisegundos)
		
		/**********ACTUALIZANDO ESTADIO con estructura JSON GRAFICABLE**********************/
		// 
		//json formato interno estandard tipo string.
		//ejemplo antes:  [date:Tue, 06 Oct 2015 09:10:52 GMT, listado:[4, 5, 6, 7.2]]
		//ejemplo antes class: groovy.json.internal.LazyMap
		//ejemplo despues :{"date":"Tue, 06 Oct 2015 09:10:52 GMT","listado":[4,5,6,7.2,0.5321]} 
		//PERO EN STRING resulta el json. CUIDADO.
		//estadio_graficable.estadio = ConversorJsonGroovyTools.instance.serializar_a_JsonString(patron_JsonString_actualizado)
		
		def dato_json = ticket as JSON
		
		//Impresor.instance.consola("Dato-ticket- como JSON: ${dato_json}",true)
		//Impresor.instance.consola( dato_json.class)
		//Impresor.instance.consola("Dato-ticket- como JSON (toString()",true)
		//Impresor.instance.consola( dato_json.toString())
		
		def dato_JsonObject = ConversorJsonGroovyTools.instance.parsear(dato_json.toString())

		
		patron_JsonObject_actualizado.put("dato", dato_JsonObject)
		
		//Impresor.instance.consola("Patron_JsonObject_Actualizado",true)
		//Impresor.instance.consola( patron_JsonObject_actualizado,true)
		
		groovy.json.internal.LazyMap patron_JsonObject_completado = patron_JsonObject_actualizado
		
		//groovy.json.internal.LazyMap patron_JsonObject_completado = //agregar_ticket_a_estructura_graficable_JsonString(ticket, patron_JsonObject_actualizado)
		
		//Impresor.instance.consola( patron_JsonObject_completado,true)
		
		String patron_JsonString_completado = ConversorJsonGroovyTools.instance.serializar_a_JsonString(patron_JsonObject_completado)

		Impresor.instance.consola("[REESTRUCTURADO].[COMPLETADO].[FORMATO]:",true)
		Impresor.instance.consola( patron_JsonString_completado)
		//Impresor.instance.consola( patron_JsonString_completado.class)
		
		estadio_graficable.estadio = patron_JsonString_completado
		/*********************************************/
		//Impresor.instance.consola( estadio_graficable.dump(),true)
		//Impresor.instance.consola( estadio_graficable.estadio.class)
		
		
		//retorno
		estadio_graficable.estadio
	}
	
	groovy.json.internal.LazyMap agregar_ticket_a_estructura_graficable_JsonString (def ticket, groovy.json.internal.LazyMap dato_JsonObject){
			
		def ticket_Json = ticket as JSON //formato JsonString
		
		Impresor.instance.consola(ticket_Json,true)	
		Impresor.instance.consola( dato_JsonObject,true)	
		
		dato_JsonObject.put("dato",ticket_Json)
		
		//retorno
		dato_JsonObject	
	}
	
	//retorna un map
	def aplicarMotorApiClojure(def ticket, String nombre_estadio_motor){
		
		def estadoActualizandose = Estadio.findByNombre(nombre_estadio_motor)
		
		/**Nota recordatorio:
		 * El formato aceptado por cheshire-plugin-clojure para DECODING.
		 * 
		 * Ejemplo Json en formato string: "{\"clave\": 5}".
		 * (Tal como está escrito.).
		 * */
		 /**JSON CONVERTER
		  * 
		  *  1. Default  -  all fields, shallow associations,
		  * 	a. render blah as JSON .
		  * 2. Global deep converter - change all JSON converters to use deep association traversal.
		  * 	a. grails.converters.json.default.deep = true
		  * 
		  * import grails.converters.*
		  * import org.codehaus.groovy.grails.web.json.*; // package containing JSONObject, JSONArray,...
		  * 
		  * def o = JSON.parse("{ foo: 'bar' }"); // Parse a JSON String
		  * assert o instanceof JSONObject // In this case, JSON.parse returns a JSONObject instance
		  * assert o instanceof Map // which implements the Map interface
		  * assert o.foo == 'bar' // access a property
		  * 
		  * // Parse another JSON String containing a Javascript Array
		  * def a = JSON.parse("[ 1, 2, 3]") 
		  * assert a instanceof JSONArray
		  * assert a instanceof List
		  * assert a[0] == 1
		  * 
		  * */
		JSON.use('deep')
		
		def ticket_JSON = ticket as JSON
		
			
		 /**
		  * EDITANDO dato 
		  * Pasos necesarios para que pueda ser,
		  * Utilizado por el decodificador basado en Clojure (Cheshire).
		  * Los '"' hay que convertirlos en '\"'.
		  * Consultar bibliografía para más detalles.  
		  * */
		
		String ticket_JSON_str = ticket_JSON.toString()
		String dato_reemplazado_caracteres = ticket_JSON_str.replace('"','\\"')
		String dato_editado_reemplazado_caracteres = "\"" + dato_reemplazado_caracteres + "\""
			
		def dato_redefinido = dato_editado_reemplazado_caracteres
			
		/**
		 *  Parametro Formato de clave: true, retorna el mapa con claves "Keyword en Clojure"
		 * */		
	
		def ticket_formato_clojure = MotorService.decodificar_JsonFormato_a_MapFormatoClojure (dato_redefinido, "true")	
		
		// "[EDITANDO] a String:"
		String dato_map_clojure = ticket_formato_clojure
		
		println "***********************"
		println "*       Aplicando      *"
		println "*   Motor-API Clojure  *"
		println "***********************"
		
		/**
		 * Aplicando API Clojure
		 * */	
		def st1 		= MotorService.process_data_dropping_signals( estadoActualizandose.estadio, dato_map_clojure)
		def sennales 	= MotorService.procesar_datos_recuperar_sennales( estadoActualizandose.estadio, dato_map_clojure)
		
		/*Señales retorna [[]] lista de lista en groovy*/
		Impresor.instance.consola("Estado nuevo: ${st1}",true)
		Impresor.instance.consola("Señales: ${sennales}",true)
		
		
		
		println "***********************"
		println "*    Dato Procesado   *"
		println "*    (actualizando)   *"
		println "*    			       *"
		println "*    (Pesistiendo)    *"
		println "***********************"
		
		/**
		 * ACTUALIZACION ESTADO TICKETS
		 * */
		def sennales_aplanadas = sennales.flatten()		
				
		estadoActualizandose.estadio = st1
		estadoActualizandose.sennales.add(sennales_aplanadas)
		
		
		Impresor.instance.consola("Aplanando Señales: ${sennales_aplanadas}",true)
		Impresor.instance.consola("ANTES: ${estadoActualizandose.sennales}") 
		//Impresor.instance.consola("Aplanando(flattening): ${estadoActualizandose.sennales.flatten()}") 
		estadoActualizandose.sennales = estadoActualizandose.sennales.flatten()
		Impresor.instance.consola("Despues: ${estadoActualizandose.sennales}")
		
		/**
		 * Si la señal-signal está expresada en fraccion, ejemplo 1/8,
		 * falla JSONSLURPER y falla todo (mensaje:"unexpected character /")
		 * No lo puede interpretar como class java.math.BigDecimal.
		 * Para ello la solución es, al estar las señales como arrayMap, serializar con JSONOUTPUT y ocurre la autoconversión
		 * */
		//estadoActualizandose.sennales = ConversorJsonGroovyTools.instance.serializar_a_JsonString(estadoActualizandose.sennales)
		//Impresor.instance.consola("Resolviendo calculo decimal( si esta expresado en fraccion): ${estadoActualizandose.sennales}")
		
		/**
		 * Fin actualizacion
		 * */
		
		//retorno de un map	
		
		["estadio":estadoActualizandose.estadio,"sennales": estadoActualizandose.sennales]		
	}
	def preparar_Estructura_para_graficar (def ticket_procesado_map){
		
		//Impresor.instance.consola( "*** Metodo preparar estructura para graficar ***",true)
		//Impresor.instance.consola(" Ticket_mapeado ${ticket_procesado_map}")
		//Impresor.instance.consola(ticket_procesado_map.class)
		
		Impresor.instance.consola("***Preparando para graficar***",true)
		
		def mapaGraficable = MotorService.mapear_aplanar_resultados_del_Estado ( ticket_procesado_map.estadio, ':Contadores')
		
		//Impresor.instance.consola( "-.-.-.-.Estructura útil para gráficos:(con def)-.-.-.-",true)
		
		//Impresor.instance.consola(mapaGraficable,true)
		
		//Impresor.instance.consola(mapaGraficable.getClass())
		
		String mapaGraficable_str = mapaGraficable
		
		//Impresor.instance.consola( "-.-Estructura útil para gráficos:(definida String)-.-.")
		
		//Impresor.instance.consola( mapaGraficable_str)
		
		Impresor.instance.consola( "*** Estructura reorganizada y formateando a JSON:(MapClojure a Json) ***")
		
		def mapaGraficable_Json = MotorService.codificar_MapFormatoClojure_a_JsonFormato (mapaGraficable)
		
		//Impresor.instance.consola( mapaGraficable_Json)
		
		//Impresor.instance.consola( mapaGraficable_Json.class) //es un string
		 
		/**Editando Dato a estructura final**/
		
		def datos_contadores 		= reorganizarDato (mapaGraficable_Json)
		
		Impresor.instance.consola( datos_contadores,true)
		
		//Impresor.instance.consola(ticket_procesado_map.sennales,true)
		
		//ejemplo:sennales:[{"Ticket-fraction-Rojo":3/10}, {"Ticket-VERDE":1/5}]
		/**
		 * *
		 *necesario porque sino falla el parser. Se ha probado con grails y groovy parsers.
		 *La fraccion inhibe el parseo. No se produce.
		 *modificaciones previas que se hacian, parsearlo a string por problemas con /, numeros racionales
		 *se suspendieron. En consecuencia: ticket_procesado_map.sennales,
		 *en este punto llega como arrayList y no como string.
		 *También en runtime, reconoce a la funcion como java.lang.String.replaceALL(),
		 *en lugar de groovy replaceALL que su argumento permite regex.
		 * 
		 * def rta = ticket_procesado_map.sennales.replaceAll(~/\:/, ':\"')
		 * 
		 * 	def rta2 = rta.replaceAll(~/\}/,'\"}') 
		***/
		//Impresor.instance.consola("Conversion a string del valor-fraccion- de la señal: ${rta2}",true)
		
		def sennales_coleccion = ticket_procesado_map.sennales
		
		//def sennales_coleccion = grails.converters.JSON.parse(rta2)//ConversorJsonGroovyTools.instance.parsear(ticket_procesado_map.sennales)
		
		//Impresor.instance.consola(sennales_coleccion,true)
		
		def rta_claves 	= sennales_coleccion.collect{ it.keySet() }
		def rta_valores = sennales_coleccion.collect{ it.values() }
		
		rta_claves 	= rta_claves.flatten()
		rta_valores	= rta_valores.flatten()
		//Lista		
		//rta_valores = rta_valores.collect{ Double.parseDouble(it)}
		
		return ["contadores":datos_contadores,"sennales":["claves": rta_claves, "valores":rta_valores]] 	 
		
	}
		/**
		 * Se modifica estructura. Se reemplazan caracteres,
		 * generan inconvientes con el parserJSONSlurper.
		 * Ejemplo (como resultado del retorno de Cheshire-Clojure-Encode-Decode): 
		 * { "Algo[\"Trulala\"]": 5} ==> Excepción.
		 * Se convierte a:
		 * { "Algo[Trulala]": 5} ==> Okey.
		 * 
		 * */
	def reorganizarDato (def dato){
		
		def edit1 		= dato.replaceAll(~/\[\\"/,'[')
		
		//Impresor.instance.consola(edit1)
		
		def edit2 = edit1.replaceAll(~/\\"\]/,']')  //(/\"\]/,'_') 
		
		//Impresor.instance.consola(edit2)
		
		def edit3	= ConversorJsonGroovyTools.instance.parsear(edit2)
		
		def claves 		= edit3.keySet() //obtengo claves, formato TreeSet
		def valores 	= edit3.values() //obtengo valores, formato TreeSet
		def lst_claves 	= [] + claves	//formato ArrayList
		def lst_valores	= [] + valores	//formato ArrayList
			
		["claves": lst_claves, "valores":lst_valores]
	}	
	def calcularTiempoProcesamientoTicket(def inicio){
		
		def fin_milisegundos = new Date().getTime() //en milisegundos
		
		def tiempo_transcurrido = fin_milisegundos - inicio		
		
		tiempo_transcurrido
		}
		
	def calcularTiempoPromedioProcesamientoPorTicket(def json_patron){
		
		double promedio = 0
		if (json_patron.tiempoProcesoPorTicket.size() > 0) { 
			//para obtener siempre dos decimales multiplico por 1.0 (una unidad tipo decimal)
			promedio = 1.0 * ( json_patron.tiempoProcesoPorTicket.sum() / json_patron.tiempoProcesoPorTicket.size() )
			
		}
		promedio.round(2)		
		}
		
	def descomponerTimeStampTicket(String formato_arg ,def fecha){
		/*Si el parametro  es java.sql.timestamp"
		 * En la base de datos se persiste con formato : "yyyy-MM-dd'T'HH:mm:ss'Z'"
		 * Formato fecha, caso específico del formato que es persistido en H2.
		 * Es casteado a otro formato distinto. Ejemplo:
		 * Recibe : "2018-06-16T23:04:39Z"
		 * Queda :2018-06-16 20:04:39.121
		 * De clase: class java.sql.Timestamp 
		 * Formato resultante: "yyyy-MM-dd HH:mm:ss.S"
		 * Que resulta en excepcion por ser no parseable.
		 * Conclusion: Formatos utilizados
		 * String formato		= "yyyy-MM-dd HH:mm:ss.S"
		 * String formato		= "yyyy-MM-dd HH:mm:ss.SSS" (variante)
		 * String formato 		= "yyyy-MM-dd HH:mm:ss" (variante)
		 * String formato_persistido 	= "yyyy-MM-dd'T'HH:mm:ss'Z'" (en H2 database)
		 * *************************************************************************
		 * En tiempo de ejecucion recibiendo:
		 * dato_fecha_cualquiera.toString() = 2018-06-16 20:54:18.312
		 * dato_fecha_cualquiera.toString() = 2018-06-16T23:54:18.312Z
		 * String formato_recibiendo 	= "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" (Ejemplo:2018-06-16T23:54:18.312Z)
		 * *************************************************************
		 * java.time.Instant <--- java.sql.Timestamp.toInstant()
		 * "Timestamp:" + timestamp.toInstant().toString())
		 * Ejemplo retorno: Timestamp:2017-01-09T18:30:00Z
		*/

		
		def tiempo_clase_Date= Date.parse(formato_arg, fecha)
		def descomposicion = [
								"annio" 	: tiempo_clase_Date[YEAR],
								"mes" 		: tiempo_clase_Date[MONTH],
								"dia"		: tiempo_clase_Date[DATE],
								"hora"		: tiempo_clase_Date[HOUR],
								"minutos"	: tiempo_clase_Date[MINUTE],
								"segundos"	: tiempo_clase_Date[SECOND]
							]
			
		descomposicion
		
	}	
			
	def configurar_Json (groovy.json.internal.LazyMap json_patron,def fecha, def inicio){	
		
		/**Calculo de tiempo de procesamiento por ticket**/
		def intervalo = calcularTiempoProcesamientoTicket(inicio)
		
		json_patron.tiempoProcesoPorTicket.add(intervalo)
		
		/**calculando Tiempo promedio Procesamiento por Ticket*/
		
		json_patron.calculados.promedioTiempoProcesamientoTicket = calcularTiempoPromedioProcesamientoPorTicket(json_patron)
		
		/******Descomposición TimeStamp ticket*************/
		//ejemplo :2018-06-16 20:54:18.312
		String formato_recibido					= "yyyy-MM-dd HH:mm:ss.SSS"
		String formato_recibido_variante		= "yyyy-MM-dd HH:mm:ss.S"
		String formato_persistido_no_recibido 	= "yyyy-MM-dd'T'HH:mm:ss'Z'"
		String formato_runtime					= "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"	
		def descomposicion = descomponerTimeStampTicket(formato_runtime,fecha)
		/**Actualizar datos descompuestos de TimeStamp*/
			json_patron.fecha.annio.add(descomposicion.annio)
			json_patron.fecha.mes.add(descomposicion.mes)
			json_patron.fecha.dia.add(descomposicion.dia)
			json_patron.fecha.hora.add(descomposicion.hora)
			json_patron.fecha.minutos.add(descomposicion.minutos)
			json_patron.fecha.segundos.add(descomposicion.segundos)
			
		//retorno Json configurado groovy.json.internal.LazyMap
		//ejemplo [date:Tue, 06 Oct 2015 09:10:52 GMT, listado:[4, 5, 6, 7.2]]
		json_patron
	}
}
