(ns grails
  (:require [clojure.core :refer :all]
	;:require [home/comunity/Workspace/ClojureProyectos/privadoRepoTP/tpValidadorReglas/TDD-2018-01-G07/MonitorEventos/src/main/resources/pruebatres :refer :all]
	))
	
	
(defn soy_una_funcion_en_carpeta_raiz []
	(let [saludo "PRUEBA_UNO: src.main.clj.prueba.clj 
	***Hola soy el retorno de una funcion de Clojure.***"]
	(println "Imprimiendo CLASSPATH")
	(println (clojure.string/join "\n" (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader)))))
	(println "Imprimiendo CLASSPATH")
	 ;(println (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))
	 (println saludo)
	
	saludo))
	
	;(defn llamandoFuncionEnSubcarpeta []
	
	;	funcion_tres[])
	

