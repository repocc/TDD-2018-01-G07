(ns motor
  (:require [clojure.core :refer :all]
  :require  [data-processor :refer :all])
  (:import [fiuba.materia7510.aplicacion.generador ClojurePojo])
)


(defn iniciar [rules]
	(.setInicializarProceso 

		(initialize-processor rules)
	)
)

(defn procesar_datos [state new-data]
	(.setProcesarDatos 
	
		(process-data state new-data)
	)
)
(defn process_data_dropping_signals [state new-data]
	(.setProcess_data_dropping_signals 

		(first (process-data state new-data))
	)
)
		
  
(defn consultar_contador [state nombreContador []]
	(.setConsultar_Contador 
		(query-counter state nombreContador [])
	)
)

(defn agregar_reglas_procesar_datos_emitir_sennales [ rules nombreContador datos]

(.setAgregar_reglas_procesar_datos_emitir_sennales 
	 (let [	st0 		(initialize-processor rules)
			st1 		(process-data-dropping-signals st0 datos)
			retorno 	st1
			]	
	 retorno)
	 )
 )
 
 
(defn procesarDatos_ModoBatch [rules nombreContador datos]	
	(let [	st0 	(initialize-processor rules)
			st1 	(process-data-dropping-signals st0 datos)
			st2		(process-data-dropping-signals st1 datos)
			rta		(query-counter st2 nombreContador [])
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

(defn main
  "Motor Clojure Para Monitorizar Eventos.[PROBANDO-TESTING].
  Se recibe un map formato:
  {:reglas '(reglas aqui) :nombreContador 'SIN NOMBRE':dato {'spam' true}}."
  [& args]
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
		](procesarDatos_ModoBatch 
								reglas
								nContador
								dato))
		(println "Adios. Argumentos incorrectos" args)
 ))
  
  
