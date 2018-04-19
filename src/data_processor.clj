(ns data-processor
  (:require [clojure.core :refer :all]
    :require [utiles.operacionesRecursivas :refer :all]
    :require [funcionesEspeciales.funcionesEspeciales :refer :all]
 ))


(def cero_inicial [0])



(defn ejecutarFuncionRecursiva [funcion state pasdo actual]
  (resolver_operacion (list funcion {:estado state :dato_pasado pasdo :dato_actual actual}))

)

(defn CargarPast [dato pasado]
	(merge pasado {dato true})
)

(defn incrementar [contador cuanto]
  (let [aux (+ cuanto (last contador))]
	[aux])
)

(defn validarCondiciones [condiciones new-data state]
  (if (= condiciones true)
    true
	   (if (= (ejecutarFuncionRecursiva condiciones state nil new-data) true)
      true
      (let [NoPasaNunca (every? identity (mapv (fn [x] ;;esto no funciona si no hay pass
              (not(ejecutarFuncionRecursiva condiciones state x new-data)))(keys (:DatosPasados state))))]
            (if (= NoPasaNunca false)
              true
              false
            )
      )
     )
  )
)

(defn incrementar_Contador_simple [counter-name argFN reglas]
    (let [clave (keyword counter-name)]
		    (let [DatosNuevos (conj(pop(clave reglas)) (incrementar (last(clave reglas)) argFN))]
            {clave DatosNuevos}
        )
    )
)

(defn incrementar_Contador_mapa [counter-name new-data argFN reglas]
		(let [condicion (second((keyword counter-name) reglas))
          parametros (first((keyword counter-name) reglas))
		      TablaSalida (last((keyword counter-name) reglas))
          tieneTodosLosParemetros (every? identity(mapv (fn [x]
     			(not= (get new-data (last x)) nil) ) parametros))
    	  	claves_tabla (keys TablaSalida)
          clave  (mapv (fn [x]
                (get new-data (last x))) parametros)
          noTengoLaClave (every? identity (mapv (fn [x]
                  (not= x clave))claves_tabla))
     ]
      (if (= tieneTodosLosParemetros true)
       		(if (= noTengoLaClave true)
          	{counter-name (conj(pop((keyword counter-name) reglas))
              (merge  TablaSalida {clave (incrementar cero_inicial argFN)}))}
            {counter-name (conj(pop((keyword counter-name) reglas))
              (merge  TablaSalida {clave (incrementar (TablaSalida clave) argFN)}))}
          )
          {counter-name (conj(pop((keyword counter-name) reglas)) TablaSalida)}
       		 )
     )
)

(defn incrementar_Contador [counter-name new-data argFN state]
   (let [parametros (first((keyword counter-name) (:Contadores state)))]
      (if (= (count parametros) 0)
		      (incrementar_Contador_simple counter-name argFN (:Contadores state))
		      (incrementar_Contador_mapa counter-name new-data argFN (:Contadores state))
      )
   )
)

(defn incrementarContadorStep [counter-name new-data argFN state]
   (let [parametros (second((keyword counter-name) (:ContadorSteps state)))]
      (if (= (count parametros) 0)
		      (incrementar_Contador_simple counter-name argFN (:ContadorSteps state))
		      (incrementar_Contador_mapa counter-name new-data argFN (:ContadorSteps state))
      )
   )
)

(defn validarContador [clave_contador new-data state]
  (let [condiciones (second((keyword clave_contador) (:Contadores state)))]
	 (if (= (validarCondiciones condiciones new-data state) true)
		(incrementar_Contador clave_contador new-data 1 state)
		{clave_contador ((keyword clave_contador) (:Contadores state))})
  )
)

(defn aplicar-reglas-Contador [state new-data]
  (mapv (fn [x]
         (validarContador x new-data state) ) (keys (:Contadores state))
  )
)

(defn validarContadorSteps [clave_contador new-data state]
  (let [condiciones (get((keyword clave_contador) (:ContadorSteps state))2)
        step (first((keyword clave_contador) (:ContadorSteps state)))]
	 (if (= (validarCondiciones condiciones new-data state) true)
    (if (number? step)
		  (incrementarContadorStep clave_contador new-data step state)
		  (incrementarContadorStep clave_contador new-data (ejecutarFuncionRecursiva step state nil new-data) state))
		{clave_contador ((keyword clave_contador) (:ContadorSteps state))})
  )
)

(defn aplicarReglasContadorSteps [state new-data]
  (mapv (fn [x]
         (validarContadorSteps x new-data state) ) (keys (:ContadorSteps state))
  )
)


(defn ejecutarSenneal [clave_Sennales new-data state]
	(let [parametros (first((keyword clave_Sennales) (:Sennales state)))
	      salida (ejecutarFuncionRecursiva parametros state nil new-data)]
        (if (= salida nil)
		      ()
          {(name clave_Sennales) salida}
	      )
  )
)

(defn validarSennales [clave_Sennales new-data state]
	(let [condiciones (second((keyword clave_Sennales) (:Sennales state)))]
	   (if (= (validarCondiciones condiciones new-data state) true)
		   (ejecutarSenneal clave_Sennales new-data state)
	   )
  )
)

(defn aplicar-reglas-Sennales [state new-data]
	(mapv (fn [x]
		(validarSennales x new-data state) ) (keys (:Sennales state)))
)

(defn process-data [state new-data]
  (let [salidaNueva (first (aplicar-reglas-Sennales state new-data))
        salidaContadores (aplicar-reglas-Contador state new-data)
        salidaContadoresSteps (aplicarReglasContadorSteps state new-data)
        pasado (CargarPast new-data (:DatosPasados state))]

  (let [salida {:Contadores (into (sorted-map) salidaContadores),
                :ContadorSteps (into (sorted-map) salidaContadoresSteps),
                :Sennales (:Sennales state),
                :DatosPasados pasado}]
   (if (= (count salidaNueva) 0)
		[salida []]
		[salida [salidaNueva]]))
  )
)

(defn query-counter [state counter-name counter-args]
  (counter-value counter-name counter-args state)
)

(defn identificarReglas [rules]
      (let [listaContadores (map #(let [[ a nombre parametros condicion & resto] %]
                 (if (= (str a) "define-counter")
                      (if (= (count parametros) 0)
            						{(keyword nombre) [parametros condicion cero_inicial]}
            						{(keyword nombre) [parametros condicion {}]})))rules)
            listaContadoresSteps (map #(let [[ a nombre step parametros condicion & resto] %]
                  (if (= (str a) "define-step-counter")
                      (if (= (count parametros) 0)
                        {(keyword nombre) [step parametros condicion cero_inicial]}
                        {(keyword nombre) [step parametros condicion {}]})))rules)
            listaSennals (map #(let [[ a nombre parametros & resto] %]
                 (if (= (str a) "define-signal")
                          {(keyword (first (keys nombre))) [(first (vals nombre)) parametros]})
                         )rules)
                  ]
          {:Contadores (into (sorted-map)listaContadores),
           :ContadorSteps (into (sorted-map)listaContadoresSteps),
           :Sennales (into (sorted-map)listaSennals),
           :DatosPasados {}}
        )
)

(defn initialize-processor [rules]
  (let [salida (identificarReglas rules)]
    salida)
  )
