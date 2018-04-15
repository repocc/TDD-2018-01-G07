(ns utiles.funcionesRecursivas
  (:require [clojure.core :refer :all]))
;estos funcionan con multiples argumentos
(declare operar_con_AND)
(declare counter-value)
(declare f4)
(declare f5)
(declare f6)
(declare determinar_operacion_matematica)
(declare operacion_mod)
;____________________________________________________
;(starts-with? s substr)
;(ends-with? s substr)
;(includes? s substr).Nota: no sirve con regex.
;(not= x y & more).  Same as (not (= obj1 obj2))

;(str)(str x)(str x & ys)
;With no args, returns the empty string. With one arg x, returns
;x.toString().  (str nil) returns the empty string. With more than
;one arg, returns the concatenation of the str values of the args.
;____________________________________________________
;Nota personal : Ubicar clojure.string en ns como requerimiento produce un par de warnings vinculados a funciones reverse y replace que ya estan en clojure.core. Para evitar conflictos, se usa donde se necesita hasta tener mas informacion.
(defn convertAND [x & args]
   (def sale (and x args))
   sale)
;funcion de prueba de ejecucion
(defn counter-value
  "ESTO ES LA SALIDA DE COUNTER VALUE.UN NUMERO DE PRUEBA"
  [x & args]

  1234)

(def diccionario_para_strings {'concat str 'includes? clojure.string/includes? 'starts-with? clojure.string/starts-with? 'ends-with? clojure.string/ends-with?})
;NOTA personal:(Insolitos resultados) no se comporta igual en las pruebas and, or.
;(#'or 5 true) retorna nil y debería ser 5.Tambien por Lein REPL.
;(or 5 true) retorna 5.El caso directo en Lein REPL.
;(#'or true 5) retorna true.Inversion del caso anterior.
;(#'clojure.core/and true false) retorna true. (and true false) retorna OK en REPL.
(def diccionario_aritmetica_comparacion {'+ + '- - '* * '/ / 'quot quot  '< < '> > '<= <= '>= >= 'or #'or 'and #'operar_con_AND '= = 'not= not= 'mod mod 'counter-value #'counter-value})

(def diccionario_oper_mono_argumentos {'not not})
(def diccionario_de_Var_a_Symbol_Func_especiales {#'counter-value 'counter-value})


(def diccionario_valor_retorno {'+ 0 '- 0 '* 1 '/ 1 'quot 1 '< 7N '> -7N '<= 7N  '>= -7N 'or false 'and true 'mod 1 'counter-value 1 })

(def resultado [])
(def repetir [])
(def valor_retorno 0)
(def delimitador_izq "(" )
(def w_contValores "counter-value")
(def w_corriente  "current")
(def w_pasado "past")

(defn obtener_Valor_Retorno
  "Se obtiene el valor de retorno para el calculo recursivo, según el tipo de operación matematica.Ejemplo + es 0; / es 1.Si no lo encuentra retorna nil."
  [valor_retorno]

  (get diccionario_valor_retorno valor_retorno)
  )
(defn obtener_Operador_No_simbolico
  "Se recupera el caracter var de un operador symbol.Si no lo encuentra retorna nil."
  [operador_symbol]

  (get diccionario_aritmetica_comparacion operador_symbol)
  )
(defn es_numero_o_booleano [x]
  ;retorna true si es numero o booleano. boolean retorna true excepto:nil,false.
  (or (number? x )(= true x) (= false x))
  )
;aplica a diccionario_aritmetica_comparacion
(defn es_Operacion_Simple [x]
  ;es un caso particular, argumentos numericos. Una secuencia con una sola operacion '(op & args).
  ;se busca que solo haya un operando.
  ;retorna valor booleano
    ;(println "Esto recibe filtrar_Operacion_Simple " x)
    (let [  a             x
         b             (first a); es operador
         c             (rest a)]; lo que no es el primer operador
         ;(println "b " b)
         ;(println "c " c)
                  ;filtrando
         (every? number? c))
  )
(defn sum [coleccion]
  ;caso simple de recursion para una suma a partir de una coleccion.
  (println (first coleccion))
  (println (rest coleccion))
  (if (empty? coleccion)
    0
    (+ (first coleccion)
       (sum (rest coleccion)))))
(defn operar_con_AND
  "Funcion recursiva,para reflejar el comportamiento de la funcion AND. Pruebas realizadas en REPL dan resultados incorrectos si, si originalmente estaba como SYMBOL y la conversion a #'and, resulta erronea."
  ;Sobrecargado
  ([] true)
  ([a & args]
    ;(println "Argumentos extra:" a args)
    (operar_con_AND (conj args a)))
  ([coleccion ]
   ;(println "parametros recibidos" coleccion)
  (let [  a                coleccion
      resto_coleccion    (rest a);sacando el primer argumento.
      prim_arg           (first a)
     ]
    ;(println "Resto de coleccion" resto_coleccion prim_arg "RTA:" (boolean prim_arg))
    ;(println "Esta vacia resto de coleccion?" (empty? resto_coleccion))

      (if (empty? resto_coleccion)
         (do
            ;(println "Realizando operacion AND final.." prim_arg)
            ;si llego hasta aqui la recursion entonces, los valores anteriores eran true, entonces solo falta esta expresion para definir el resultado.
            (boolean prim_arg)
         )
            (if (boolean prim_arg)
                 (operar_con_AND resto_coleccion)
                 false))
         )))
(defn operar_comparando [operador valor_retorno coleccion]
 ;recursion  para operacion matematica a partir de una coleccion.
 ;recibe la funcion aritmEtica como primer Argumento.
 ;recibe el valor de retorno, al estar vacIa la coleccion, que depende de la funcion:0,1.
  (let [  a                coleccion
      resto_coleccion    (rest a);sacando el primer argumento.
      prim_arg           (first a)
     ]
    ;(println "Resto de coleccion" resto_coleccion prim_arg )
    ;(println "Esta vacia resto de coleccion?" (empty? resto_coleccion))
    (if (or (empty? coleccion)(not (first coleccion)))
      valor_retorno
      (if (and (not (coll? prim_arg))(empty? resto_coleccion))
         (do
            ;(println "Realizando operacion comparacion final.." prim_arg)
            prim_arg
         )
         (if (and (not (coll? prim_arg))(boolean prim_arg))
           (operador prim_arg
             (operar_comparando + valor_retorno resto_coleccion))

           (operador
             (determinar_operacion_matematica (first prim_arg)
                (obtener_Operador_No_simbolico (first prim_arg))
                (obtener_Valor_Retorno (first prim_arg)) (rest prim_arg))
              (operar_comparando + valor_retorno resto_coleccion))
                 );if

         ))))

(defn operacion_aritmetica [operador valor_retorno coleccion]
  ;recursion  para operacion matematica a partir de una coleccion.
  ;recibe la funcion aritmEtica como primer Argumento.
  ;recibe el valor de retorno, al estar vacIa la coleccion, que depende de la funcion:0,1.
  ;si no es numero el primer elemento,entonces es un operando y es una expresion.
 ;(println "Recibe operacion_aritmetica :" operador valor_retorno coleccion)

 (let [  a                coleccion
       resto_coleccion    (rest a);sacando el primer argumento.
       prim_arg           (first a)

      ]
     ;(println "tipo operador" (type operador) valor_retorno )
     ;(println "Resto de coleccion" resto_coleccion prim_arg )
     ;(println "Esta vacia resto de coleccion?" (empty? resto_coleccion))

 (if (or (empty? coleccion)(not (first coleccion)))
    (do
      ;(println "Determinar operacion matematica entra valor retorno" valor_retorno)
   valor_retorno)
   (if (and (not (coll? prim_arg))(empty? resto_coleccion))
      (do
         ;(println "Realizando operacion aritmetica final..." "operador"operador " argumento" prim_arg)
         prim_arg
      )
      (if  (and (not (coll? prim_arg))(number? prim_arg))
       (do

          ;(println "esta ingresando aqui para calculo" prim_arg resto_coleccion)
          (operador prim_arg (operacion_aritmetica operador valor_retorno resto_coleccion))

        )
(do

          (operador (determinar_operacion_matematica (first prim_arg) (obtener_Operador_No_simbolico (first prim_arg)) (obtener_Valor_Retorno (first prim_arg)) (rest prim_arg))
              (operacion_aritmetica operador valor_retorno resto_coleccion))
    )
)))))

      ;NOTA personal: el valor de retorno se calcula mientras la operacion sea symbol.
      ;sino encuentra retorna nil.
      ;(def valor_retorno (get diccionario_valor_retorno b))
      ;alternativa sin usar diccionario.
      ;(def intermedio (if (symbol? b) (resolve b) b))
      ; b es el symbol que interesa resolver.
(defn operacion_mod [operador valor_retorno coleccion]
  ;si faltan los dos argumentos necesarios
  ;(println "Ejecutando operacion_mod..." operador coleccion)
  (let [  a                   coleccion
        b                     (rest a);sacando el primer argumento.
        prim_arg             (first a)
        seg_arg              (first b)
        resto_coleccion       (rest b);sacando el segundo argumento
       ]
      ;(println "Resto de coleccion" resto_coleccion prim_arg seg_arg)
      ;empty funcion sobre colecciones solamente
      ;(println "Esta vacio o faltan argumentos?"
      ;(or (empty? coleccion)(and (not (first coleccion))(not (second coleccion)))))
      ;(println "Esta vacia resto de coleccion?" (empty? resto_coleccion))

  (if (or (empty? coleccion)(and (not (first coleccion))(not (second coleccion))))
    valor_retorno
    (if (and (not (coll? prim_arg))(not (coll? seg_arg))(empty? resto_coleccion))
      (do
        ;(println "Realizando operacion mod..............RTA"  (operador prim_arg seg_arg) )
        (operador prim_arg seg_arg)
        )
      (if (and (not (coll? prim_arg))(not (coll? seg_arg))
        (number? prim_arg)(number? seg_arg))

        (operador prim_arg seg_arg
            (operacion_mod operador valor_retorno (resto_coleccion)))


        (operador
            (determinar_operacion_matematica (first prim_arg) (obtener_Operador_No_simbolico (first prim_arg))
            (obtener_Valor_Retorno (first prim_arg)) (rest prim_arg))
              (determinar_operacion_matematica (first seg_arg) (obtener_Operador_No_simbolico (first seg_arg))
              (obtener_Valor_Retorno (first seg_arg)) (rest seg_arg))
                (operacion_mod operador valor_retorno resto_coleccion)))))))

(defn determinar_operacion_matematica
  "Determina que operacion matematica continua ejecutandose segun el operador recibidos por parametro. ademas de los otros argumentos requeridos por las funciones"
  [operador_symbol operador valor_retorno coleccion]

  (let [op operador_symbol]
    ;(println "coleccion que llega a la operacion determinar_operacion_matematica:" coleccion)

    (cond
      (or (= op '=)(= op 'not=)(= op '>=)(= op '<=)(= op '>)(= op '<))
            (operar_comparando operador valor_retorno coleccion)
      (or (= op '+)(= op '-)(= op '/)(= op 'quot)(= op '*)(= op 'or))
            (operacion_aritmetica operador valor_retorno coleccion)
      (= op 'and)
            (operar_con_AND coleccion )
      (= op 'mod)
            (operacion_mod operador valor_retorno coleccion)
        :else "Falta algo por resolver."
      )))

(defn f4 [x]
  ;(println "Esto recibe f4 " x)
  ;Desestructurando expresion : [operador,valor_retorno,coleccion sin operador]
 (let [  a             x
         b             (first a)
         c             (rest a)
         d             (first c)
         e             (rest c)
         valor_retorno (get diccionario_valor_retorno b)
         operador      (get diccionario_aritmetica_comparacion b)  ]

   ;(println "valor de retorno f4" valor_retorno)
   ;empty funciona en colecciones.
   (if (empty? a)
       valor_retorno
       (try

      (do      ;El parametro b es el operador formato symbol.
          (determinar_operacion_matematica b operador valor_retorno c)
        )
        (catch ArithmeticException e
          (println "Probable division por cero [f4]...")
          nil)
        (catch ClassCastException e
          (println "Es probable que el problema sea con un string, coleccion vacia [f4].")

          nil)
        (catch Exception e
          (println "Alguna otra excepcion no capturada [f4]...")
          nil)
        )

    )))
(defn recursar_f6 [operador  coleccion]
    ;llama f6 .parte de operacion recursiva de la misma funcion.
    ;Desesctrutura la expresion separando el operador del resto de la coleccion.
    (let [op   (get diccionario_aritmetica_comparacion operador)
        vr    (get diccionario_valor_retorno operador)
        col   coleccion]
    (f6 op vr col);expresion anidada
    ))
(defn f6 [operador valor_retorno coleccion]
  ;actua sobre colecciones de funciones , no sobre una llamada a funcion concreta.
  ;Dicho de otra manera, actua funcion aplicada sobre otras expresiones.
  ;Desestructurando expresion:separo operador del resto de la expresion.
  ;(println "Esto recibe f6 " operador valor_retorno coleccion)
  (let [  a             coleccion
          b             (first a); es una llamada a funcion. EJ: "(+ 5 3)"
          c             (rest a); el resto de la coleccion.
        ]
     ;(println "El ultimo de la coleccion" (last a))
     ;(println "El primero del resto de la coleccion " (first c))
     ;(println "La cantidad de elementos en la coleccion " (count a))
     ;(println "b " b)
     ;(println "c " c)
     ;(println "valor de retorno f6 " valor_retorno)
 ;empty funciona en colecciones.Si una llamada a funcion es () anula toda la operatoria.
  (if (empty? a)
    (do
       ;(println "Entro aqui por valor_retorno [f6]" valor_retorno)
     valor_retorno)
  (try
;*****************************************************************************
       ;CASOS PARTICULARES-FUNCIONES ESPECIFICAS
       ;si es una expresion de funcion particular como counter-value.
    (cond
      ;condicion
      (not= nil (get diccionario_de_Var_a_Symbol_Func_especiales operador))
        ;en este caso es var.
        (operador coleccion)
        ;condicion
      (and (not (es_numero_o_booleano b)) (coll? b)
      (not= nil (get diccionario_aritmetica_comparacion (first b)))
      (or (= (first b) 'counter-value )
        (= (first b) 'current )
        (= (first b) 'past)))
        ;en este caso es un symbol.Hay que convertirlo.
        (do
          ;(println "Entro en ultima expresion" b )
          ;(println "El calculo da: "((get diccionario_aritmetica_comparacion (first b))(rest b)) )
        (operador ((get diccionario_aritmetica_comparacion (first b))(rest b))
          (if (and (coll? c)(not (empty? c)))
            (f6 operador valor_retorno c)valor_retorno))
              )
      ;condicion: si es numero o bool ,funcion apply no compila.
      (and (coll? b) (not (es_numero_o_booleano (first c))) (not (empty? c))
      (or  (clojure.string/includes?(apply str (first c)) w_contValores)
           (clojure.string/includes?(apply str (first c)) w_corriente)
           (clojure.string/includes?(apply str (first c)) w_pasado)))
           ;Tiene funciones especiales entonces deben resolverse en esta funcion;expresion anidada.

            (operador (recursar_f6 (first b) (rest b));Ejemplo operador(+ ()())
            (recursar_f6 (first (first c)) (rest (first c))))
              ;esta dentro de la coleccion '((c)).

;****************************************************************************
      ;condicion:el segundo y el primero son numeros.
      (= true (and  (= (count a) 2) (= (first c) (last a))
              (es_numero_o_booleano b)
              (es_numero_o_booleano (first c))))

              (operador b  (first c))
      ;condicion
      (= true (and (= (count a) 2) (= (first c) (last a))
        (if (es_numero_o_booleano (first c)) true
        (not(clojure.string/includes?(apply str (first c)) delimitador_izq )))))

        (if (es_numero_o_booleano b)

          (operador b
          (if (and (= 1 (count c))(es_numero_o_booleano (first c)))
            (first c)(f4 (first c))))

          (operador (f4 b)
          (if (and (= 1 (count c))(es_numero_o_booleano (first c)))
            (first c)(f4 (first c)))))
;*************************Expresiones anidadas*****************************
      ;quedan dos elementos de la coleccion completa.

      ;condicion
      (= false (and (= (count a) 2) (= (first c) (last a))
        (if (es_numero_o_booleano (first c)) true
        (not (clojure.string/includes?(apply str (first c)) delimitador_izq )))))

        (if (es_numero_o_booleano b)

          (operador b
            (if (and (= (count a) 2)(= (first c)(last a))
              (clojure.string/includes? (apply str (first c)) delimitador_izq ))
              (recursar_f6  (first (first c)) (rest (first c)));expresion anidada
              (f6 operador valor_retorno c)))

          (operador (f4 b)
            (if (and (= (count a) 2)(= (first c)(last a))
              (clojure.string/includes? (apply str (first c)) delimitador_izq ))
              (recursar_f6  (first (first c)) (rest (first c)));expresion anidada
              (f6 operador valor_retorno c))))

        :else "Caso no resuelto para los parametros pasados");end-cond

  (catch ArithmeticException e
    (println "Probable division por cero.[f6]...")
    nil)
  (catch NullPointerException e
      ;caso  b=(+ 5 3) c=(/ 2 0)=nil
    (println "Probable division por cero [f6]...")
    nil));end-try-catch
  )))


(defn f5 [x]
  ;(println "Esto recibe f5 " x)
  (let [  a             x
       b             (first a); es operador
       c             (rest a); lo que no es el primer operador
       d             (first c)
       e             (rest c)
       valor_retorno (get diccionario_valor_retorno b)
       ;el diccionario_aritmetica_comparacion esta listo, pero sin usar por ahora
       opera      (get diccionario_aritmetica_comparacion b)
       operador    (if (symbol? opera) (resolve opera) opera)]
       ;(println "b " b)
       ;(println "c " c)
       ;(println "d " d)
       ;(println "e " e)
       ;(println "valor de retorno f CINCO" valor_retorno)
       ;empty funciona en colecciones.
       (if (empty? a)
           valor_retorno
           (try
              ;Determinando si es una expresion de una funcion particular
              ;Ejemplo signal.

              (if (es_Operacion_Simple a)
                (f4 a)
                (f6 operador valor_retorno c))
            (catch ArithmeticException e
              (println "Probable division por cero f5...")
              nil)
            (catch ClassCastException e
              (println "Es probable que el problema sea con un string? [f5]")
              nil)
        ;    (catch Exception e
        ;      (println "Alguna otra excepcion no capturada...")
        ;      nil)
            )
            )))
