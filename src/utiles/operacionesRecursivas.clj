(ns utiles.operacionesRecursivas
  (:require [clojure.core :refer :all]
  :require [definiciones.definiciones :refer :all]))

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
	[coleccion]
 (if (empty? coleccion) ()
   (let [
	        primer_operando 	  (first coleccion)
	        resto_coleccion 	  (rest coleccion)
	        resultado_recursion	(list (iniciar_recursion_en_operaciones_logicas resto_coleccion))
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
                              (ejecutar_operacion primer_operando)
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
	[coleccion]

   (if (empty? coleccion) ()
     (let [
          	primer_operando 	  (first coleccion)
          	resto_coleccion 	  (rest coleccion)
          	resultado_recursion	(list (iniciar_recursion_en_operaciones_igualdad resto_coleccion))
  	      ]

        	(list
        		(if (coll? primer_operando)
        			     (ejecutar_operacion primer_operando)
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
	[coleccion]
   (if (empty? coleccion) ()
     (let [
          	primer_operando 	(first coleccion)
          	resto_coleccion 	(rest coleccion)
          	resultado_recursion 	(list (iniciar_recursion_en_operaciones_aritmeticas resto_coleccion))
          	]
        (cond
        	(or 	(and  (number? primer_operando) (boolean primer_operando))
        		    (and  (coll? primer_operando)
                      (not(empty? primer_operando))
                      (not= ''() primer_operando))
        	);or
              	(list
              		  (if (coll? primer_operando)
                       (ejecutar_operacion primer_operando)
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
	[coleccion]
  (if (empty? coleccion) ()
    (let [
        	primer_operando      (first coleccion)
        	resto_coleccion      (rest coleccion)
          resultado_recursion  (list(iniciar_recursion_en_operaciones_comparativas
              resto_coleccion))
      	  ]
      (cond
      	(or	(number? primer_operando)
      		  (and  (coll? primer_operando)
                  (not(empty? primer_operando))
                  (not= ''() primer_operando))
      	);or
          	 (list
          		   (if (coll? primer_operando)
          			       (ejecutar_operacion primer_operando)
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
	[coleccion]
   (if (empty? coleccion) ()
     (let [
          	primer_operando 	  (first coleccion)
          	resto_coleccion 	  (rest coleccion)
          	cantidad_operandos	(count coleccion)
            resultado_recursion (list (iniciar_recursion_en_operaciones_mod  resto_coleccion))
  	      ]


     (cond

  			(or 	(and (number? primer_operando) (boolean primer_operando))
  					  (and (coll? primer_operando)
              (not(empty? primer_operando))
              (not= ''() primer_operando))
  			);or
      			(list
      				(if (coll? primer_operando)
      					(ejecutar_operacion primer_operando)
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
  [coleccion]

  {:operador (first coleccion) :resto_coleccion (rest coleccion) :operador_var (obtener_operador_var_de_symbol (first coleccion)) :primer_operando (second coleccion) :cantidad_operandos (count (rest coleccion))}
);end-defn
;******************************************************************
(defn desestructurar_coleccion_y_calcular_funciones_de_igualdad
  "Desestructura la colección en operador, operandos y resto de la colección."
  [coleccion]

   (let [	desestructurada         (desestructurar coleccion)
          resto_coleccion 	       (:resto_coleccion desestructurada)
      		operador_var		         (:operador_var desestructurada)
      		resultado_recursion	     (flatten
            (iniciar_recursion_en_operaciones_igualdad resto_coleccion ))
  	    ]

  		(apply operador_var resultado_recursion	);end-apply
    );end-let
);end-funcion
;*******************************************************************

(defn desestructurar_coleccion_y_calcular_funciones_logicas
  "Desestructura la coleccion en operador, operandos y resto de la colección. Para operaciones lógicas."
  [coleccion]
 (let [	desestructurada      (desestructurar coleccion)
        operador 			       (:operador desestructurada)
    		resto_coleccion 	   (:resto_coleccion desestructurada)
    		operador_var		         (:operador_var desestructurada)
    	  resultado_recursion 	(flatten
          (iniciar_recursion_en_operaciones_logicas  resto_coleccion));end-flatten
		]


      (operador_var resultado_recursion	);end-apply

 );end-let
);end-funcion
;******************************************************************

(defn desestructurar_coleccion_y_calcular_Funcion_MOD
  "Desestructura la coleccion en operador, operandos y resto de la coleccion.Operador mod que recibe dos argumentos."
  [coleccion]

 (let [	desestructurada         (desestructurar coleccion)
        resto_coleccion 	      (:resto_coleccion desestructurada)
        operador_var		        (:operador_var desestructurada)
      	primer_operando 			  (second coleccion)
      	cantidad_operandos			(:cantidad_operandos desestructurada)
      	resultado_recursion			(flatten
          (iniciar_recursion_en_operaciones_mod resto_coleccion));end-flatten
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
  [coleccion]
 (let [	desestructurada         (desestructurar coleccion)
        resto_coleccion 	      (:resto_coleccion desestructurada)
        operador_var		        (:operador_var desestructurada)
    		resultado_recursion	(flatten
          (iniciar_recursion_en_operaciones_comparativas resto_coleccion))
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
 [coleccion]

 (let [		desestructurada         (desestructurar coleccion)
          resto_coleccion 	      (:resto_coleccion desestructurada)
          operador_var		        (:operador_var desestructurada)
      		resultado_recursion	  (flatten
            (iniciar_recursion_en_operaciones_aritmeticas resto_coleccion
						))
      ]


			(apply operador_var resultado_recursion	);end-apply
		;	);end-if
	);end-let
);end-funcion
;****************************************************************************
(defn desestructurar_coleccion_y_calcular_Funcion_NOT
	"Desestructura la coleccion en operador, operandos y resto de la coleccion.Operador NOT que recibe UN argumento."
	[coleccion]

 (let [	desestructurada         (desestructurar coleccion)
        resto_coleccion 	      (:resto_coleccion desestructurada)
        operador_var		        (:operador_var desestructurada)
      	cantidad_operandos			(:cantidad_operandos desestructurada)
      	resultado_recursion		 (flatten
          (iniciar_recursion_en_operaciones_igualdad resto_coleccion));end-flatten
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
(defn determinar_operacion [colec]

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

(defmethod ejecutar_operacion :Igualdad resolver_operaciones_de_igualdad [coleccion]

	(desestructurar_coleccion_y_calcular_funciones_de_igualdad coleccion));end-metodo
(defmethod ejecutar_operacion :Aritmetica resolver_operacion_aritmetica [coleccion]

	(desestructurar_coleccion_y_calcular_funciones_aritmeticas coleccion));end-metodo
(defmethod ejecutar_operacion :Comparativa resolver_operacion_comparativa [coleccion]

	(desestructurar_coleccion_y_calcular_funciones_comparativas coleccion));end-metodo
(defmethod ejecutar_operacion :Logica resolver_operacion_logica [coleccion]

	(desestructurar_coleccion_y_calcular_funciones_logicas coleccion));end-metodo
(defmethod ejecutar_operacion :MOD resolver_operacion_mod [coleccion]


	(desestructurar_coleccion_y_calcular_Funcion_MOD coleccion));end-metodo
(defmethod ejecutar_operacion :NOT resolver_operacion_not [coleccion]

	(desestructurar_coleccion_y_calcular_Funcion_NOT coleccion));end-metodo
(defmethod ejecutar_operacion :Funcion_Especial resolver_operaciones_especiales
  ;"Funciones de trabajo practico.Ej: counter-value, current, past"
   [coleccion]

   (let [ coll      coleccion
          operador  (get diccionario_op_Func_especiales (first coleccion))
          resto_argumentos (rest coll)
          resultado     ( operador resto_argumentos)
     ]
    resultado
      );end-let
 );end-metodo
(defmethod ejecutar_operacion :Funcion_Para_Strings resolver_operaciones_en_strings
  ;"Funciones requeridas para strings. Abarca: includes?, starts-with?, ends-with?"
   [coleccion]

     ((get diccionario_op_strings (first coleccion)) (rest coleccion))
 )
(defmethod ejecutar_operacion :Coleccion_Vacia retornar_coleccion_Vacia [coleccion]
  ;"Funcion para cuando se recibe una coleccion sin elementos. retorna ()."

  ());end-defn
(defmethod ejecutar_operacion nil operacion_anulada [coleccion]
 (str "OPeracion anulada. operador no reconocido, division por cero?"))
(defmethod ejecutar_operacion :default operacion_Argumentos_Incorrectos [coleccion]
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
  '(= 2 (mod 5 3))."
  [coleccion]

  (try
     (ejecutar_operacion coleccion)
   (catch ArithmeticException e
     (println "No se puede dividir por cero ...Ejemplo (/ 7 0); (/ 7 (mod 5 5)).")
     nil)
     (catch IllegalArgumentException e
       (println "Error en el numero de argumentos pasados a la funcion. Ejemplo (mod 5 3 4)")
       nil)
     (catch ClassCastException e
     (println "Argumento invalido para la operacion que se intenta realizar.Ejemplo (mod 5 true).")
     nil)
     (catch NullPointerException e
       (println "Debido a valor nil por operacion imposible de manejar este argumento.Ejemplo:(mod 5 nil) ")
     nil)
   (catch Exception e
     (println "Alguna otra excepcion no capturada ...")
     nil)
   )
     );end-defn
