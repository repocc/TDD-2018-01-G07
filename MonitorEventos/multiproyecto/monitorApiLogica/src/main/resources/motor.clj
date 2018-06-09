(ns motor
  (:require [data-processor :refer :all]))

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
