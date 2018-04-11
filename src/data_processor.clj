(ns data-processor
  (:require [clojure.core :refer :all]
   :use [utiles.funcionesRecursivas :refer :all]))
          ;;  [clojure.string :refer :all]))

(def valor_cero 0)
(def cero_inicial [0])
(def estado [])
(def contadores {});; {:counter-name [valores almacenados]}
(def sennales {});; reglas tipo signal
(def salidaSennales {})
(def listaPast {})
(def LaX 0)
(def newData 0)

(defn past [clave]
	(get LaX clave)	
)

(defn current [clave]
	(get newData clave)
)

(defn CargarPast [dato]
	(def listaPast (merge listaPast {dato true}))
)

(defn counter-value [counter-name counter-args]
  (def salidaCV ((keyword counter-name) contadores))  
  (if (= salidaCV nil) 
	(def salida 0)
	(if (= (count (first salidaCV)) 0)
	    (def salidaCV (last(last salidaCV)))
	    (def salidaCV (last(get (last salidaCV) counter-args))))
   )  salidaCV
)

(defn counter-value [counter-name counter-args]
  (def salidaCV ((keyword counter-name) contadores))  
  (if (= salidaCV nil) 
	(def salida 0)
	(if (= (count (first salidaCV)) 0)
	    (def salidaCV (last(last salidaCV)))
	    (def salidaCV (last(get (last salidaCV) counter-args))))
   )  salidaCV
)


(defn actualizar_Estado []
  ;;autoincremental en una unidad según el ultimo estado almacenado
  (def estado ( conj estado  (inc (last estado)))))
  
(defn incrementar [contador argFN]
	(conj contador (argFN(last contador)))
)
         
(defn incrementar_Contador_simple [counter-name argFN]
  ;;incrementa el contador una unidad a partir de su ultimo valor almacenado
    (let [clave (keyword counter-name)]
		(def DatosNuevos  [(first(clave contadores)) (second(clave contadores)) (incrementar (last(clave contadores)) argFN)])
         (def contadores (merge  contadores {clave DatosNuevos}))
         ))
  
(defn incrementar_Contador_mapa [counter-name new-data argFN]
		 (def flag true)
		 (def condicion (second((keyword counter-name) contadores)))
		 (def parametros (first((keyword counter-name) contadores)))
		 (def TablaSalida (last((keyword counter-name) contadores)))
		 (def tipo "nada")
		 (def clave [])
		 (mapv (fn [x]
			(if (= (get new-data (last x)) nil) (def flag false) (def clave (conj clave (get new-data (last x)))))
			) parametros)
		 (if (= flag true)
			()
			(def argFN +)
		 )
		 (def claves_tabla (keys TablaSalida)) 
		 (def noTengoLaClave true)
		 (if (not= (count claves_tabla) 0)
			(mapv (fn [x]
				(if (= x clave) (def noTengoLaClave false))
			)claves_tabla)
			)
		 (if (= noTengoLaClave true) 
			(def TablaSalida (merge  TablaSalida {clave cero_inicial}))
			)			
		 (def claves_tabla (keys TablaSalida)) 
		 
	     (def aux cero_inicial)
		 (mapv (fn [x]
			(def contador (TablaSalida x))
			 (if (= x clave) (def aux (incrementar contador argFN)) (def aux(incrementar contador +)))
			 (def TablaSalida (merge  TablaSalida {x aux}))
     	 )claves_tabla)	  
		 (def DatosNuevos  [parametros condicion TablaSalida])
		 (def contadores (merge  contadores {counter-name DatosNuevos})) 
)	  
         
(defn incrementar_Contador [counter-name new-data argFN]
   (def parametros (first((keyword counter-name) contadores)))
   (if (= (count parametros) 0)
		(incrementar_Contador_simple counter-name argFN)
		(incrementar_Contador_mapa counter-name new-data argFN)		
    )
)              

(defn actualizar_Contadores [counter-name valor] ;;Agregar Contador
  (let [clave (keyword counter-name)]
       (def contadores (merge  contadores { clave (conj  valor)})))
)

(defn actualizar_Sennales [nameSennales valor]
  (let [clave (keyword nameSennales)]
	(def sennales (merge  sennales { clave (conj  valor)}))
  )
)

(defn identificar-reglas [rules]
  ;se reinician todas las colecciones de reglas a vacias para reprocesamiento
  ;sucede en los test.
  (def contadores {})
  (def sennales {})
  (map #(let [[ a nombre parametros condicion & resto] %]
             (if (= (str a) "define-counter")
                 (do
                     (if (= (count parametros) 0)
						(actualizar_Contadores (str nombre) [parametros condicion cero_inicial])
						(actualizar_Contadores (str nombre) [parametros condicion {}])))
                 (do
                     (actualizar_Sennales (first (keys nombre)) [(first (vals nombre)) parametros]))))

        rules)
    );;map


(defn initialize-processor [rules]

  (def estado [valor_cero])

  (let [  etapa1 (identificar-reglas rules)
          etapa2  (if (> (count etapa1) valor_cero)
                    (first estado)
                    nil)]
          ;let externo
       etapa2))
;end-defn

(defn validarCondiciones [clave_contador new-data]
	(def condiciones (second((keyword clave_contador) contadores)))
	(def flag true)
	(def claves (keys new-data))
	(def tipo "nada")
	(mapv (fn [x]
		(if (= (str tipo) "current") 
			(if (= (get new-data x) true) () (def flag false)))
		(if (= (str x) "current") (def tipo "current") (def tipo "nada"))
	) condiciones)
	(if (= flag true)
		(incrementar_Contador clave_contador new-data inc)
		(incrementar_Contador clave_contador new-data +))
)

(defn validarContador [clave_contador new-data]
	(def condiciones (second((keyword clave_contador) contadores)))
	(if (= condiciones true) 
		(incrementar_Contador clave_contador new-data inc)
		(validarCondiciones clave_contador new-data))
)
	
	
(defn aplicar-reglas-Contador [new-data]
  (def claves_de_count (keys contadores))
  (mapv (fn [x]
         (validarContador x new-data) ) claves_de_count
  )
)

(defn ejecutar [fn A B]
	(def funciones2 #{ "counter-value"})
	(def RSalida nil)	
	(if (= "counter-value" (str fn)) (def RSalida (counter-value A B)))
	(if (= "current" (str fn)) (def RSalida (current A)))	
	(if (= "past" (str fn)) (def RSalida (past A)))			
	(if (= "=" (str fn)) (def RSalida (= A B)))	
	(if (= "/" (str fn))
		(if (= B 0)
			(println "divide por cero")
			(def RSalida(/ A B))
		)
	)
	RSalida
)

(defn ejecutarFuncionRecursiva [funcion]
	(def funciones #{"/" "counter-value" "=" "current" "past"})
	(if (= (contains? funciones (str (first funcion))) true)
		(def salida (ejecutar (first funcion) (ejecutarFuncionRecursiva (second funcion)) (ejecutarFuncionRecursiva (last funcion))))
		(def salida funcion)
	)
	salida
) 

(defn ejecutarSenneal [clave_Sennales new-data]
	(def parametros (first((keyword clave_Sennales) sennales)))
	(def newData new-data)
	(def salida (ejecutarFuncionRecursiva parametros))
	(if (= salida nil)
		()
		(def salidaSennales (merge  salidaSennales {(name clave_Sennales) salida}))
	)
	
	salidaSennales
)

(defn validarCondicionesSennales [clave_Sennales new-data]
	(def funcion (second(clave_Sennales sennales)))	
	(def flag false)
	(def claves_past (keys listaPast))
	(def newData new-data)
	(mapv (fn [x]
		(def LaX x)
		(if (ejecutarFuncionRecursiva funcion)
		(def flag true))
	) claves_past)

	(if (= flag true)
		(ejecutarSenneal clave_Sennales new-data)
	)
)

(defn validarSennales [clave_Sennales new-data]
	(def condiciones (second((keyword clave_Sennales) sennales)))
	(if (= condiciones true) 
		(ejecutarSenneal clave_Sennales new-data)
		(validarCondicionesSennales clave_Sennales new-data)
	)
)

(defn aplicar-reglas-Sennales [new-data]
	(def salidaSennales {})
	(def claves_de_Sennales (keys sennales))
	(mapv (fn [x]
		(validarSennales x new-data) ) claves_de_Sennales)
)

(defn process-data [state new-data]
  ;;se incrementa el estado por cada ejecución
	(actualizar_Estado)
	(aplicar-reglas-Sennales new-data)
	(if (empty? new-data)
		[(last estado) '()] ;porque hay un signal-skip-on-error-test
		(do
			(aplicar-reglas-Contador new-data)
			(CargarPast new-data)
		))
	(if (= (count salidaSennales) 0)
		(def salida [contadores []])	
		(def salida [contadores [salidaSennales]])
	)
	salida
)

;end-defn;;[nil []])

  ;;retorna el valor numerico almacenado en ese contador para esos argumentos
  ;;aun no se sabe que argumentos son y que calculo hacer sobre ellos, por lo tanto se aplica funcion que retorna 0.
  ;;fn [state counter-name counter-args]
  ;;por lo pronto puedo retornar el estado actual de ese contador ignorando el resto de los argumentos
  ;;el estado correspondería con el indice del vector que tiene valor para esa clave.
  
(defn query-counter [state counter-name  counter-args]
  (def salida ((keyword counter-name) contadores))  
  (if (= salida nil) 
	(def salida 0)
	(if (= (count (first salida)) 0)
	    (def salida (last(last salida)))
	    (def salida (last(get (last salida) counter-args))))
   )
  salida
)
