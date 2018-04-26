(ns grails.procesadorDatosTodoEnUno
;  (:gen-class)
  (:require [clojure.core :refer :all]
    ))
(defn quienSoy[]
  (println "Soy el motor, procesadorDatosTodoEnUno."))
:********************************************************************
:********************PROCESADOR DE DATOS*****************************
:***********************TODO EN UNO**********************************
:********************************************************************
:********************************************************************
:********************************************************************
;;(ns funcionesEspeciales.funcionesEspeciales
;;  (:require [clojure.core :refer :all]
;;   ))

(defn operar_con_AND [coleccion]
  (every? boolean coleccion)
  )
(defn operar_con_OR [coleccion]
  ;Nota personal (CASOS):
    ;;user=> (boolean false)
    ;;false
    ;;user=> (or nil false)
    ;;false
    ;;user=> (or false nil)
    ;;nil
    ;;user=> (#'or false nil)
    ;;nil
    ;;user=> (#'or nil false)
    ;;nil
    ;;user=> (some boolean '(nil false))
    ;;nil
    ;;Se toma el supuesto de retornar false ante la ambigüedad.
  (let [retorno (some boolean coleccion)]
      (cond
        (= nil retorno)
            false
        :else retorno
)));end-defn
;***************************************************************
(defn past [clave pasdo]
	(get pasdo clave)
)

(defn current [clave actual]
	(get actual clave)
)

(defn counter-value [counter-name counter-args state]
  (let [contador ((keyword counter-name) (:Contadores state))]
    (if (= contador nil)
        (let [contadorStep ((keyword counter-name) (:ContadorSteps state))]
          (if (= contadorStep nil)
            0
            (if (= (count (second contadorStep)) 0)
                (last(last contadorStep))
                (last(get (last contadorStep) counter-args)))
          )
        )
	     (if (= (count (first contador)) 0)
	         (last(last contador))
	         (last(get (last contador) counter-args)))
    )
  )
)
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
;;		(ns definiciones.definiciones
 ;; (:require [clojure.core :refer :all]
 ;;  :require [funcionesEspeciales.funcionesEspeciales :refer :all]
 ;;  ))


  ;**********************Diccionarios********************************

  (def diccionario_op_igualdades {'= = 'not= not=})
  (def diccionario_op_logicas {'and #'operar_con_AND 'or #'operar_con_OR})
  (def diccionario_op_comparativas {'< < '> > '<= <= '>= >=})
  (def diccionario_op_aritmeticas {'+ + '- - '* * '/ / 'quot quot})
  (def diccionario_op_NOT {'not not});casos particulares
  (def diccionario_op_MOD {'mod mod});casos particulares
  (def diccionario_op_Func_especiales {'counter-value #'counter-value 'current #'current 'past #'past})
  (def diccionario_op_strings {'concat str 'includes? clojure.string/includes? 'starts-with? clojure.string/starts-with? 'ends-with? clojure.string/ends-with?})

:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
(ns utiles.operacionesRecursivas
  (:require [clojure.core :refer :all]
  :require [definiciones.definiciones :refer :all]
  ;:require [funcionesEspeciales.funcionesEspeciales :refer [operar_con_AND operar_con_OR counter-value current past]]
  ))

(declare ejecutar_operacion )

;**************Funciones para diccionarios*************************

(defn obtener_operador_var_de_symbol
  "Se recupera el caracter var de un operador symbol.Si no lo encuentra retorna nil."
  [operador_symbol]
    (let [op_igualdad       (get diccionario_op_igualdades operador_symbol)
          op_logica         (get diccionario_op_logicas operador_symbol)
          op_comparativa    (get diccionario_op_comparativas operador_symbol)
          op_aritmetica     (get diccionario_op_aritmeticas operador_symbol)
          op_not            (get diccionario_op_NOT operador_symbol)
          op_mod            (get diccionario_op_MOD operador_symbol)
          op_f_especial     (get diccionario_op_Func_especiales operador_symbol)
          op_string         (get diccionario_op_strings operador_symbol)
      ]
 (or op_igualdad op_logica op_comparativa op_aritmetica op_not op_mod op_f_especial op_string)));end-defn
;******************************************************************
;*********Funciones recursivas requeridas para MULTIMETODO*********
(defn iniciar_recursion_en_operaciones_logicas
	"Ocurre la recursion de la funcion."
	[coleccion estado_condicionado]
 (if (empty? coleccion) ()
   (let [
	        primer_operando 	  (first coleccion)
	        resto_coleccion 	  (rest coleccion)
	        resultado_recursion	(list (iniciar_recursion_en_operaciones_logicas resto_coleccion estado_condicionado))
	      ]

        (cond ;condicion
    	       (or  (boolean primer_operando)
                  (= nil primer_operando)
                  (= false primer_operando)
    		          (and  (coll? primer_operando)
                        (not(empty? primer_operando))
                        (not= ''() primer_operando))
    	        );or
                  	  (list
                          (if (coll? primer_operando)
                              (ejecutar_operacion
                                primer_operando estado_condicionado)
                  			       primer_operando
                            );end-if
                  		      resultado_recursion
                  	     );end-first-list
    	        :else nil
    	    );cond
      );end-let
 );end-if
);end-funcion
;******************************************************************

(defn iniciar_recursion_en_operaciones_igualdad
	"Ocurre la recursion de la funcion.Para todo valor.Caso = y not=."
	[coleccion estado_condicionado]

   (if (empty? coleccion) ()
     (let [
          	primer_operando 	  (first coleccion)
          	resto_coleccion 	  (rest coleccion)
          	resultado_recursion	(list (iniciar_recursion_en_operaciones_igualdad resto_coleccion estado_condicionado))
  	      ]

        	(list
        		(if (coll? primer_operando)
        			     (ejecutar_operacion primer_operando estado_condicionado)
        			      primer_operando
        		  );end-if
        		        resultado_recursion
        		);end-first-list
        );end-let
   );end-if
);end-funcion
;******************************************************************

(defn iniciar_recursion_en_operaciones_aritmeticas
	"Ocurre la recursion de la funcion.Operaciones aritmeticas."
	[coleccion estado_condicionado]
   (if (empty? coleccion) ()
     (let [
          	primer_operando 	(first coleccion)
          	resto_coleccion 	(rest coleccion)
          	resultado_recursion 	(list (iniciar_recursion_en_operaciones_aritmeticas resto_coleccion estado_condicionado))
          	]
        (cond
        	(or 	(and  (number? primer_operando) (boolean primer_operando))
        		    (and  (coll? primer_operando)
                      (not(empty? primer_operando))
                      (not= ''() primer_operando))
        	);or
              	(list
              		  (if (coll? primer_operando)
                       (ejecutar_operacion
                         primer_operando estado_condicionado)
                        primer_operando
                      );end-if
              		      resultado_recursion
              		);end-first-list
        	:else nil
  	    );cond
     );end-let
   );end-if
);end-funcion
;******************************************************************

(defn iniciar_recursion_en_operaciones_comparativas
	"Ocurre la recursion de la funcion."
	[coleccion estado_condicionado]
  (if (empty? coleccion) ()
    (let [
        	primer_operando      (first coleccion)
        	resto_coleccion      (rest coleccion)
          resultado_recursion  (list(iniciar_recursion_en_operaciones_comparativas
              resto_coleccion estado_condicionado))
      	  ]
      (cond
      	(or	(number? primer_operando)
      		  (and  (coll? primer_operando)
                  (not(empty? primer_operando))
                  (not= ''() primer_operando))
      	);or
          	 (list
          		   (if (coll? primer_operando)
          			       (ejecutar_operacion primer_operando estado_condicionado)
          			        primer_operando
          		    );end-if
          		      resultado_recursion
          	  );end-first-list
        :else nil
        );end-cond
    );end-let
  );end-if
);end-funcion
;**************************************************************************

(defn iniciar_recursion_en_operaciones_mod
	"Ocurre la recursion de la funcion mod"
	[coleccion estado_condicionado]
   (if (empty? coleccion) ()
     (let [
          	primer_operando 	  (first coleccion)
          	resto_coleccion 	  (rest coleccion)
          	cantidad_operandos	(count coleccion)
            resultado_recursion (list (iniciar_recursion_en_operaciones_mod  resto_coleccion estado_condicionado))
  	      ]


     (cond

  			(or 	(and (number? primer_operando) (boolean primer_operando))
  					  (and (coll? primer_operando)
              (not(empty? primer_operando))
              (not= ''() primer_operando))
  			);or
      			(list
      				(if (coll? primer_operando)
      					(ejecutar_operacion primer_operando estado_condicionado)
      					primer_operando
      				);end-if
              resultado_recursion
      			);end-first-list
  	    :else nil
  	 );end-cond
    );end-let
   );end-if
);end-funcion
;*********************Funciones de desestructuracion************************

(defn desestructurar
  "Desestructura coleccion en  algunos elementos utiles para otras funciones."
  [coleccion estado_condicionado]

  {:operador (first coleccion) :resto_coleccion (rest coleccion) :operador_var (obtener_operador_var_de_symbol (first coleccion)) :primer_operando (second coleccion) :cantidad_operandos (count (rest coleccion)) :estado_cond estado_condicionado}
);end-defn
;******************************************************************
(defn desestructurar_coleccion_y_calcular_funciones_de_igualdad
  "Desestructura la colección en operador, operandos y resto de la colección."
  [coleccion estado_condicionado]

   (let [	desestructurada         (desestructurar coleccion estado_condicionado)
          resto_coleccion 	       (:resto_coleccion desestructurada)
      		operador_var		         (:operador_var desestructurada)
      		resultado_recursion	     (flatten
            (iniciar_recursion_en_operaciones_igualdad
              resto_coleccion estado_condicionado))
  	    ]

  		(apply operador_var resultado_recursion	);end-apply
    );end-let
);end-funcion
;*******************************************************************

(defn desestructurar_coleccion_y_calcular_funciones_logicas
  "Desestructura la coleccion en operador, operandos y resto de la colección. Para operaciones lógicas."
  [coleccion estado_condicionado]
 (let [	desestructurada      (desestructurar coleccion estado_condicionado)
        operador 			       (:operador desestructurada)
    		resto_coleccion 	   (:resto_coleccion desestructurada)
    		operador_var		         (:operador_var desestructurada)
    	  resultado_recursion 	(flatten
          (iniciar_recursion_en_operaciones_logicas
            resto_coleccion estado_condicionado));end-flatten
		]


      (operador_var resultado_recursion	);end-apply

 );end-let
);end-funcion
;******************************************************************

(defn desestructurar_coleccion_y_calcular_Funcion_MOD
  "Desestructura la coleccion en operador, operandos y resto de la coleccion.Operador mod que recibe dos argumentos."
  [coleccion estado_condicionado]

 (let [	desestructurada         (desestructurar coleccion estado_condicionado)
        resto_coleccion 	      (:resto_coleccion desestructurada)
        operador_var		        (:operador_var desestructurada)
      	primer_operando 			  (second coleccion)
      	cantidad_operandos			(:cantidad_operandos desestructurada)
      	resultado_recursion			(flatten
          (iniciar_recursion_en_operaciones_mod
            resto_coleccion estado_condicionado));end-flatten
	    ]

         (cond
      	(= 2 cantidad_operandos)

      			(apply operador_var resultado_recursion	);end-apply
      		;);end-if
      	:else nil ;Retorna nil si no son dos argumentos entonces la operacion mod no se puede realizar (ArityException)
          );end-cond
 );end-let
);end-funcion
;*****************************************************************************
(defn desestructurar_coleccion_y_calcular_funciones_comparativas
  "Desestructura la coleccion en operador, operandos y resto de la coleccion."
  [coleccion estado_condicionado]
 (let [	desestructurada         (desestructurar coleccion estado_condicionado)
        resto_coleccion 	      (:resto_coleccion desestructurada)
        operador_var		        (:operador_var desestructurada)
    		resultado_recursion	(flatten
          (iniciar_recursion_en_operaciones_comparativas resto_coleccion estado_condicionado))
      ]

			(apply operador_var resultado_recursion	);end-apply
		;);end-if
  );end-let
);end-funcion

;*****************************************************************************
;NOta personal: Un operando puede ser cero en comparativas,
; 'pero en operaciones aritmeticas tengo (/ 1 0)  y (* 1 1/0).Por ejemplo.'
;****************************************************************************

(defn desestructurar_coleccion_y_calcular_funciones_aritmeticas
 "Desestructura la coleccion en operador, operandos y resto de la coleccion.Control de la division por cero"
 [coleccion estado_condicionado]

 (let [		desestructurada         (desestructurar coleccion estado_condicionado)
          resto_coleccion 	      (:resto_coleccion desestructurada)
          operador_var		        (:operador_var desestructurada)

      		resultado_recursion	  (flatten
            (iniciar_recursion_en_operaciones_aritmeticas resto_coleccion estado_condicionado
						))
      ]


			(apply operador_var resultado_recursion	);end-apply
		;	);end-if
	);end-let
);end-funcion
;****************************************************************************
(defn desestructurar_coleccion_y_calcular_Funcion_NOT
	"Desestructura la coleccion en operador, operandos y resto de la coleccion.Operador NOT que recibe UN argumento."
	[coleccion estado_condicionado]

 (let [	desestructurada         (desestructurar coleccion estado_condicionado)
        resto_coleccion 	      (:resto_coleccion desestructurada)
        operador_var		        (:operador_var desestructurada)
      	cantidad_operandos			(:cantidad_operandos desestructurada)
      	resultado_recursion		 (flatten
          (iniciar_recursion_en_operaciones_igualdad resto_coleccion estado_condicionado));end-flatten
	    ]

       (cond
	       (= 1 cantidad_operandos)
		       (apply operador_var resultado_recursion	);end-apply
	       :else nil
        );end-cond
  );end-let
);end-funcion
;************************************************************************
;************************MULTIMETODO*************************************
;************************************************************************
(defn determinar_operacion [colec estado_condicionado]


  (let [operador_symbol   (first colec)
        op_igualdad       (get diccionario_op_igualdades operador_symbol)
        op_logica         (get diccionario_op_logicas operador_symbol)
        op_comparativa    (get diccionario_op_comparativas operador_symbol)
        op_aritmetica     (get diccionario_op_aritmeticas operador_symbol)
        op_not            (get diccionario_op_NOT operador_symbol)
        op_mod            (get diccionario_op_MOD operador_symbol)
        op_f_especial     (get diccionario_op_Func_especiales operador_symbol)
        op_string         (get diccionario_op_strings operador_symbol)
    ]
  (cond
    	  (and (coll? colec) (empty? colec))		:Coleccion_Vacia
	      (not= nil op_aritmetica) 		          :Aritmetica
	      (not= nil op_comparativa)		          :Comparativa
	      (not= nil op_igualdad) 	              :Igualdad
	      (not= nil op_logica) 	                :Logica
        (not= nil op_mod)		                  :MOD
        (not= nil op_not)		                  :NOT
    	  (not= nil op_f_especial) 		          :Funcion_Especial
    	  (not= nil op_string)  	              :Funcion_Para_Strings
    :else nil )));end-defn

;Definicion de MULTIMETODO.
(defmulti ejecutar_operacion determinar_operacion)

;****************************METODOS************************************

(defmethod ejecutar_operacion :Igualdad resolver_operaciones_de_igualdad [coleccion estado_condicionado]

	(desestructurar_coleccion_y_calcular_funciones_de_igualdad
    coleccion estado_condicionado));end-metodo
(defmethod ejecutar_operacion :Aritmetica resolver_operacion_aritmetica [coleccion estado_condicionado]

	(desestructurar_coleccion_y_calcular_funciones_aritmeticas
    coleccion estado_condicionado));end-metodo
(defmethod ejecutar_operacion :Comparativa resolver_operacion_comparativa [coleccion estado_condicionado]

	(desestructurar_coleccion_y_calcular_funciones_comparativas
    coleccion estado_condicionado));end-metodo
(defmethod ejecutar_operacion :Logica resolver_operacion_logica [coleccion estado_condicionado]
	(desestructurar_coleccion_y_calcular_funciones_logicas
    coleccion estado_condicionado));end-metodo
(defmethod ejecutar_operacion :MOD resolver_operacion_mod [coleccion estado_condicionado]

	(desestructurar_coleccion_y_calcular_Funcion_MOD
     coleccion estado_condicionado));end-metodo
(defmethod ejecutar_operacion :NOT resolver_operacion_not [coleccion estado_condicionado]

	(desestructurar_coleccion_y_calcular_Funcion_NOT
    coleccion estado_condicionado));end-metodo
(defmethod ejecutar_operacion :Funcion_Especial resolver_operaciones_especiales [coleccion estado_condicionado]

   (let [ colec                 coleccion
          operador_symbol       (first colec)
          operador              (get diccionario_op_Func_especiales
                                  (first colec))
          primer_arg_expresion  (second colec)
          argumentos_map        estado_condicionado

          estado                (:estado argumentos_map)
             ]

     (cond

     (= 'counter-value operador_symbol)
          ;counter-value nombre args estado
          (operador primer_arg_expresion (second (rest colec)) estado )
      (= 'current   operador_symbol)
        (let [ dato_actual      (:dato_actual argumentos_map)]

              ( operador primer_arg_expresion dato_actual)

        );end-let
      (= 'past operador_symbol )
        (let [ dato_pasado      (:dato_pasado argumentos_map)]

              ( operador primer_arg_expresion dato_pasado)
        );end-let
      :else "No hay funcion implementada para esa expresion."
      );end-cond
    ; (println "CONTAR resto argumentos " (count resto_argumentos))

      );end-let
 );end-metodo
(defmethod ejecutar_operacion :Funcion_Para_Strings resolver_operaciones_en_strings
  ;"Funciones requeridas para strings. Abarca: includes?, starts-with?, ends-with?"
   [coleccion estado_condicionado]

     ((get diccionario_op_strings (first coleccion)) (rest coleccion))
 )
(defmethod ejecutar_operacion :Coleccion_Vacia retornar_coleccion_Vacia [coleccion estado_condicionado]
  ;"Funcion para cuando se recibe una coleccion sin elementos. retorna ()."

  ());end-defn
(defmethod ejecutar_operacion nil operacion_anulada [coleccion estado_condicionado]
 (str "OPeracion anulada. operador no reconocido, division por cero?"))
(defmethod ejecutar_operacion :default operacion_Argumentos_Incorrectos [coleccion estado_condicionado]
 (throw (IllegalArgumentException.
          (str "Argumentos incorrectos " (coleccion "Coleccion") " :probable poblema de aridad?"))))
;**********************************FIN-METODOS***********************************
(defn resolver_operacion
  "Retorna el resultado de la operación que se recibe tipo List.
  Ejemplos:
  '(+ 5 3)
  '(mod 5 3)
  '(and true false 5)
  '(<= 10 9 8 (+ 5 2))
  '(str Hola Mundo)
  '(= 2 (mod 5 3)).
  Considerando que hay un argumento adicional para ejecutar_operacion:
  recibe ( arg1 arg2). Donde arg1 es la expresion (coleccion en si misma de operaciones) y arg2 es un valor, 'estado o condicion', de ser necesario como en el caso de funcionesEspeciales (counter, current, etc)."

  [coleccion]

  (try
    ;;El nuevo pasaje de parametros obliga a considerar el estado
    ;;por lo tanto se da la dualidad de considerar la ejecutar_operacion
    ;;de operaciones exclusivas como estaba diseñado e incorporar
    ;;un nuevo argumento requerido, en principio para calculos de funcionesEspeciales.
    ;;caso contrario no se utiliza.
    (let [solo_expresion "NULO_ESTADO"]

        (cond
          (not (map?(last coleccion)));solo expresiones-calculo puro-sin-estado.
                (ejecutar_operacion coleccion solo_expresion)
          (= 2 (count coleccion))
          (let [expresion (first coleccion)
                estado    (second coleccion)]
                (ejecutar_operacion expresion estado)
                );end-let

          :else "NO HACER NADA, NO ES UNA EXPRESION VALIDA."
          );end-cond
      );end-let
   (catch ArithmeticException e
     (println "No se puede dividir por cero ...")
     nil)
     (catch IllegalArgumentException e
       (println "Error en el numero de argumentos pasados a la funcion.")
       nil)
     (catch ClassCastException e
     (println "Argumento invalido para la operacion que se intenta realizar.")
     nil)
     (catch NullPointerException e
       (println "Debido a valor nil por operacion imposible de manejar este argumento.")
     nil)
   (catch Exception e
     (println "Alguna otra excepcion no capturada ...")
     nil)
   )
     );end-defn


:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
;;(ns data-processor
 ;; (:require [clojure.core :refer :all]
 ;;   :require [utiles.operacionesRecursivas :refer :all]
 ;;   :require [funcionesEspeciales.funcionesEspeciales :refer :all]
;; ))


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
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
:********************************************************************
;;(ns motor
;;  (:gen-class)
;;  (:require [clojure.core :refer :all]
;;  :require  [data-processor :refer :all]))

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
