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
