(ns grails.prueba_cero
  (:require [clojure.core :refer :all]))

(defn funcion_cero []
	(let [saludo "PRUEBA_CERO:-namespace grails.prueba_cero- src.main.clj.prueba_cero.clj 
	***Hola soy el retorno de una funcion de Clojure.***"]
	 (println saludo)
	
	saludo)
	
)
