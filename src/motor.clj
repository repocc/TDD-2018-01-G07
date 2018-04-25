(ns motor
  (:gen-class)
  (:require [clojure.core :refer :all]
  :require  [data-processor :refer :all]))




(defn process-data-dropping-signals [state new-data]
  (first (process-data state new-data)))

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

(defn -main
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
  
  
