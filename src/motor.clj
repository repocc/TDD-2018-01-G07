(ns motor
  (:require 	[data-processor :refer :all]
				[cheshire.core :refer :all]))

(defn saludar []
	(println "Hola Mundo. Soy el motor-clojure.")
	(println "Soy el motor-clojure.")
	(println "Primer cuatrimestre 2018") 
	(println "Trabajo Practico Monitorizar Eventos.")
	(println "Grupo 07: FONTANA, RODRIGUEZ.")
	(println "_______________________________________")
	(println "")
	  
)

(defn iniciar [rules]

	(initialize-processor rules)
	
)
(defn procesar_datos [state new-data]
	
		(process-data state new-data)	
)
(defn process_data_dropping_signals [state new-data]
(first (procesar_datos state new-data))
	
)

(defn consultar_contador [state nombreContador []]
 
 (query-counter state nombreContador [])
)

;;***************************************************************
;; procesar_datos y recuperar sennales
(defn procesar_datos_recuperar_sennales [state new-data]
(second (procesar_datos state new-data))

) 

;;***************************************************************
;;Convertir Dato en formato JSON a formato soportado por proyecto:api clojure
;;keyFormato: true (:foo) o false ()

(defn decodificar_JsonFormato_a_MapFormatoClojure [datoJSON keyFormato]
	(println datoJSON)
	(if (keyFormato)
		(parse-string datoJSON true)
		(parse-string datoJSON))
)
(defn codificar_MapFormatoClojure_a_JsonFormato [datoMapFormatoClojure]
	
		(generate-string datoMapFormatoClojure)
		
)

;;***************************************************************
;; Dar Formato para graficar a :contadores :sennales de ESTADO(state)
 (defn vectorizar [ mapaEstado]
 	
 (let [	coleccion mapaEstado
	resto_coleccion		(rest coleccion)
 	par_clave_valor 	 (first coleccion) 		
 	clave 		 (first par_clave_valor)
 	valor  (last (last par_clave_valor))
 	 ]
 	 (println "_______________")
 	 (println "Coleccion actual: " coleccion)
 	 (println "_______________")
 	 (println "clave: " clave)
 	 (println "_______________")
 	 (println "valor_final:" valor)
 	 (println "_______________")
 	 (println "_______________")
 	
 	(if-not (empty? resto_coleccion)	

	 	(if (= (type valor) (type []))
	 		(conj	[clave (last valor)] (vectorizar resto_coleccion))		
	 		
	 		(conj 
		 		(apply vector(flatten (map vector 
		 		(apply vector (map keyword (map #(str (name clave) % ) (keys valor))))
		 		(into [](flatten (vals valor))))))
		 		(vectorizar resto_coleccion)))
		(if (= (type valor) (type []))
	 		[clave (last valor)]		
	 			 		 
	 		(apply vector(flatten (map vector 
	 		(apply vector (map keyword (map #(str (name clave) % ) (keys valor))))
	 		(into [](flatten (vals valor)))))))	 		
	 );end if-not
))
 
 ;/*******************************************/
 ;;se pasa por parametro por ejemplo diccionario de contadores de Estado(state)
 (defn mapear_aplanar_resultados_del_Estado [mapa]
 (if (empty? mapa)
 	{}
 (let [
	coleccion 			 mapa
 	
 	resultado 			 (apply hash-map (flatten(vectorizar coleccion ))) ] 
    resultado )))

;;***********EJEMPLO SALIDA ESTADO*********************************
; {:Contadores {:Ticket-Contador [[] true [6]], :Ticket-Contador-Emisor [[(current EMISOR)] true {[AUGUSTO] [5], [Euriterco] [1]}], :Ticket-Contador-Rojo [[] (starts-with? (current ESTADIO) R) [3]], :enviado-por [[(current EMISOR)] (and (= (current EMISOR) (past EMISOR)) (includes? (past tema) (current tema))) {[AUGUSTO] [4]}]}, :ContadorSteps {}, :Sennales {:Ticket-fraction-Rojo [(/ (counter-value Ticket-Contador-Rojo []) (counter-value Ticket-Contador [])) true]}, :DatosPasados {{Ticket 12345, EMISOR AUGUSTO, RECEPTOR Propiedad de Arevalo, ESTADIO Rojo, tema colores} true, {Ticket 54321, EMISOR AUGUSTO, RECEPTOR Propiedad de Arevalo, ESTADIO Azul, tema colores} true, {Ticket 111, EMISOR Euriterco, RECEPTOR Propiedad de Arevalo, ESTADIO Verde, tema colores} true}}

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;(defn iniciar_test [rules]		
;;	
;;	(let [	st0 		(apply list (read-string rules))	
;;			st1 		(type st0)
;;			retorno 		st0
;;		]	
;;		(println rules)
;;		(println "Imprimiendo estado 0" st0)
;;		(println "Imprimiendo estado 1" st1)
		
;; retorno))			

(defn agregar_reglas_procesar_datos_emitir_sennales [ rules datos]
 (let [	st0 		(iniciar rules)
		st1 		(process_data_dropping_signals st0 datos)
		retorno 	st1
		]	
 retorno)
 )
 
(defn procesarDatos_ModoBatch [rules nombreContador datos]	
	(let [	st0 	(iniciar rules)
			st1 	(process_data_dropping_signals st0 datos)
			st2		(process_data_dropping_signals st1 datos)
			rta		(consultar_contador st2 nombreContador [])
			retorno {"Estado Inicial" st0 "Estado Procesando" st2 "ConsultaContador" rta}
		]
			
		(println "*************RETORNO PROCESO BATCH*****************")
		(println "...")
		(println retorno)
		(println "...Fin")
		(println "****Desestructurando map****.")
		(println "...")
		(println (get retorno "Estado Inicial" ))
		(println "______________________________")
		(println (get retorno "Estado Procesado" ))
		(println "______________________________")
		(println (get retorno "ConsultaContador" ))
		(println "...Fin")
		(println "*************FIN PROCESO BATCH*********************")
		
		retorno))

	
(defn principal
  "Motor Clojure Para Monitorizar Eventos.[PROBANDO-TESTING].
  Se recibe un map formato:
  {:reglas '(reglas aqui) :nombreContador 'SIN NOMBRE':dato {'spam' true}}."
  ([]
	(principal "NADA QUE ACOTAR")
  )
  ([& args]
  (println "Soy el motor-clojure.")
  (println "Primer cuatrimestre 2018") 
  (println "Trabajo Practico Monitorizar Eventos.")
  (println "Grupo 07: FONTANA, RODRIGUEZ.")
  (println "_______________________________________")
  (println "")
  (println "Argumentos recibidos:***" args "***cantidad argumentos:" (count args)"***")
  (println "")
  (if (and (map? (first args))(= 3 (count (keys (first args) ))))
		(let [	mapa				(first args)
				reglas 				(:reglas mapa) 
				nContador 			(:nombreContador mapa)
				dato				(:dato mapa)
		]
		(println "RETORNO DEL PROCESO BATCH EN FUNCION PRINCIPAL")
		(procesarDatos_ModoBatch 
								reglas
								nContador
								dato))
		
 )))
 
