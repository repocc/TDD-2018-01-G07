package fiuba.materia7510.aplicacion.utilidad


interface Constantes {
	
 static final String REGLA_PRIMERA 	= "Primero"
 static final String REGLA_SEGUNDA 	= "Segunda"
 static final String REGLA_FULL		= "Full" //combinadas primera y segunda
 static final String REGLAMENTO_1 		= '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
 static final String REGLAMENTO_2		= '((define-counter "Ticket-Contador" [] true)(define-counter "Ticket-Contador-Titulo" [(current "titulo")] true) (define-counter "Ticket-Contador-Rojo" [] (starts-with? (current "descripcion") "R"))(define-counter "Ticket-Contador-Propietario" [(current "propietario")] true) (define-signal {"Ticket-fraction-Rojo" (/ (counter-value "Ticket-Contador-Rojo" [])(counter-value "Ticket-Contador" []))}))'
 static final String REGLAMENTO_FULL 	= '((define-counter "email-count" [] true)(define-counter "spam-count" [] (= (current "titulo") "spam-count"))  (define-counter "spam-important-table" [(current "spam")(current "important")]true) (define-counter "Ticket-Contador" [] true) (define-counter "Ticket-Contador-Titulo" [(current "titulo")] true) (define-counter "Ticket-Contador-Rojo" [] (starts-with? (current "descripcion") "R"))(define-counter "Ticket-Contador-Propietario" [(current "propietario")] true) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-signal {"Ticket-fraction-Rojo" (/ (counter-value "Ticket-Contador-Rojo" [])(counter-value "Ticket-Contador" []))}true)(define-signal {"correos-color-Rojo-fraccion" (/ (counter-value "email-count" [])(counter-value "Ticket-Contador-Rojo" []))}true))'
 static final String REGLA_Signal_Tst	= '((define-signal {"correos-color-Rojo-fraccion" (/ (counter-value "email-count" [])(counter-value "Ticket-Contador-Rojo" []))}true))'
 static final String NOMBRE_ESTADIO_MOTOR_DESARROLLO 	= 'estado_Motor_API_Clojure_Desarrollo' //utilizado para desarrollo, tests.
 static final String NOMBRE_ESTADIO_MOTOR_PRODUCCION 	= 'estado_Motor_API_Clojure_Produccion' //utilizado para producción.
 
 static final String NOMBRE_ESTADIO_PLANTILLA_JSON 			= 'estadio_Plantilla_JSON'
 static final String NOMBRE_ESTADIO_PLANTILLA_JSON_DESARROLLO	= 'estadio_Graficacion_plantilla_JSON_desarrollo'
 static final String NOMBRE_ESTADIO_PLANTILLA_JSON_PRODUCCION	= 'estadio_Graficacion_plantilla_JSON_produccion'
 
 static final String ESTRUCTURA_JSON_BASICA_EXTENSIBLE ='''{"contadores":{"claves":[],"valores":[]},"sennales":{"claves":[],"valores":[]},"fecha":{"annio":[], "mes":[], "dia":[], "hora":[], "minutos":[], "segundos": []},"tiempoProcesoPorTicket":[],"calculados":{ "promedioTicketsPorHora":0, "promedioTiempoProcesamientoTicket":0, "mes":[], "hora":[] },"dato":0}'''

		 							
}
	

