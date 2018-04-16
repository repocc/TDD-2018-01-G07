(ns data-processor
  (:require [clojure.core :refer :all]
   :require [utiles.operacionesRecursivas :refer [resolver_operacion]]
   :require [definiciones.definiciones :refer :all]
   :require [funcionesEspeciales.funcionesEspeciales :refer :all]))
          ;;  [clojure.string :refer :all]))



(defn CargarPast [dato]
	(listaPast (merge listaPast {dato true}))
)

(defn actualizar_Estado []
  ;;autoincremental en una unidad según el ultimo estado almacenado
  ( estado ( conj estado  (inc (last estado)))))

(defn incrementar [contador argFN]
	(conj contador (argFN(last contador)))
)

(defn incrementar_Contador_simple [counter-name argFN]
  ;;incrementa el contador una unidad a partir de su ultimo valor almacenado
    (let [clave (keyword counter-name)]
		(def DatosNuevos  [(first(clave contadores)) (second(clave contadores)) (incrementar (last(clave contadores)) argFN)])
         (contadores (merge  contadores {clave DatosNuevos}))
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
		 (contadores (merge  contadores {counter-name DatosNuevos}))
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
       (contadores (merge  contadores { clave (conj  valor)})))
)

(defn actualizar_Sennales [nameSennales valor]
  (let [clave (keyword nameSennales)]
	(sennales (merge  sennales { clave (conj  valor)}))
  )
)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;Estructura
;estado=
;;{:reglas
;;;;;;;;;{:define-counter
;;;;;;;;;;;;;;;;;;;;;;;;{:email-count[] :spam-count[] }
;;;;;;;;;;:define-signal {"spam-fraction"{} "repeated" {}}}
;; :Datos [{vector de mapa de datos}] }

(defn agregar_Contadores [counter-name valor reglas_map]
  (let [clave           (keyword counter-name)
       regla_agregada   (assoc-in reglas_map [:define-counter] (merge (:define-counter reglas_map) {clave (conj  valor)}))

       ]

       (println "regla contador agregada" regla_agregada)
       regla_agregada
       ))
(defn agregar_Sennales [nameSennales valor reglas_map]
  (let [clave (keyword nameSennales)
        regla_agregada  (assoc-in reglas_map [:define-signal] (merge (:define-signal reglas_map) { clave (conj  valor)}))

        ]
        (println "regla signal agregada" regla_agregada )
        regla_agregada
	  ))

(defn adicionar_regla [primer_regla reglas_map]
  (let [[ a nombre parametros condicion & resto] primer_regla]
    (if (= (str a) "define-counter")

         (if (= (count parametros) valor_cero)
          (agregar_Contadores (str nombre)
                              [parametros condicion cero_inicial]
                              reglas_map
                              )
          (agregar_Contadores (str nombre)
                                  [parametros condicion {}]
                                  reglas_map))

          (agregar_Sennales (first (keys nombre))
                            [(first (vals nombre)) parametros]
                            reglas_map
                            )

        );end-if

    );end-let
  )
(defn agregar_Reglas [reglas_map rules]
 ;recursivo
 (let [
        primer_regla  (first rules)
        resto_reglas   (rest rules)
   ]
    ;(println "Reglas agregandose" primer_regla)
    ;(println "Reglas agregadas" reglas_map)
     (if (empty? rules)
        reglas_map
        (agregar_Reglas (adicionar_regla primer_regla reglas_map) resto_reglas))

   );end-let
);end-defn

(defn identificar_reglas_retornar_estado [rules]

  (println "Estas son las reglas" rules)
  (let [reglas              rules
        reglas_map_formato  {:define-counter {} :define-signal {} }
        reglas_map          (agregar_Reglas reglas_map_formato reglas)
        estado              {:reglas reglas_map :datos [{}] }
        ]
  estado)
);end-defn

(defn initialize-processor [rules]
  (if (and(coll? rules)(empty? rules))
      nil
      (identificar_reglas_retornar_estado rules))
);end-defn
(defn buscar_coincidencia
  "Retorna valor numerico del contador,que coincide con los parametros."
  [parametros regla_contador]

  ;recursivo
  (let [
         coleccion      regla_contador
         primer_valor  (first coleccion)
         resto_valores   (rest coleccion)
         argumento_contador (first primer_valor)
         valor_acumulado (last (last) primer_valor)
    ]
      (if (empty? coleccion)
         nil

         (if (= argumento_contador parametros)
            valor_acumulado
            (buscar_coincidencia (parametros resto_valores))))

    );end-let
  )
(defn query-counter [state counter-name  counter-args]
  (println "estado" state)
  (let [estado              state
        nombre_contador     counter-name
        key_nombre_contador (keyword nombre_contador)
        regla (get-in estado [:reglas :define-counter key_nombre_contador ])
        parametros_contador counter-args

        ]
        (if (= regla nil)
            nil
            ;si counter-args es [] vacio significa que
            ;la regla no tiene parametros y es un solo contador total
            (if (= (count (first parametros_contador)) 0)
                 (last(last regla))
                 ;sino es el caso:
                 ;tengo que mapear la regla buscando en los
                 ;valores acumulados VECTOR DE VECTORES? el que coincida con counter-args
                 ;ejemplo [true true] y retorno el valor del contador.
                 ;de manera recursiva.
                 (buscar_coincidencia parametros_contador regla))

                 );end-if
         );end-let

);end-defn



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn identificar-reglas [rules]
  ;se reinician todas las colecciones de reglas a vacias para reprocesamiento
  ;sucede en los test.
  (println "Estas son las reglas" rules)
  ( contadores {})
  ( sennales {})
  (map #(let [[ a nombre parametros condicion & resto] %]
             (if (= (str a) "define-counter")
                 (do
                     (if (= (count parametros) 0)
						(actualizar_Contadores (str nombre) [parametros condicion cero_inicial])
						(actualizar_Contadores (str nombre) [parametros condicion {}])))
                 (do
                     (actualizar_Sennales (first (keys nombre)) [(first (vals nombre)) parametros]))))

        rules);end-map
);end-defn


(defn initialize-proc____ [rules]
  (println "estado" estado "tipo" (type estado))
  ;(estado [valor_cero])

  (let [  etapa1 (identificar-reglas rules)
          etapa2  (if (> (count etapa1) valor_cero)
                    (first estado)
                    nil)]
          (println "identificar-reglas" etapa1)
       etapa2);let externo
);end-defn

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
	( newData new-data)
	(def salida (resolver_operacion parametros))
	(if (= salida nil)
		()
		( salidaSennales (merge  salidaSennales {(name clave_Sennales) salida}))
	)

	salidaSennales
)

(defn validarCondicionesSennales [clave_Sennales new-data]
	(def funcion (second(clave_Sennales sennales)))
	(def flag false)
	(def claves_past (keys listaPast))
	( newData new-data)
	(mapv (fn [x]
		( LaX x)
		(if (resolver_operacion funcion)
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
	(salidaSennales {})
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
  (println "contadores " contadores)
  (def salida ((keyword counter-name) contadores))
  (if (= salida nil)
	(def salida 0)
	(if (= (count (first salida)) 0)
	    (def salida (last(last salida)))
	    (def salida (last(get (last salida) counter-args))))
   )
  salida
)
