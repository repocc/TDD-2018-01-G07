(ns utiles.operacionesRecursivas-test
  (:require [clojure.test :refer :all]
            [utiles.operacionesRecursivas :refer :all]
            [data-processor :refer :all]))
(def test1 '(+ 5 3))
(def test2  '(5 3))
	;;;;;;;;;;;;;;;;;;;STRINGS;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	;;(starts-with? s substr)
	;;(ends-with? s substr)
	;;(includes? s substr)
	;;funcin concat es str: 
	;;(str)(str x)(str x & ys)
	;;With no args, returns the empty string. With one arg x, returns
	;;x.toString().  (str nil) returns the empty string. With more than
	;;one arg, returns the concatenation of the str values of the args.
(defn process-data-dropping-signals [state new-data]
  (first (process-data state new-data)))
  
(def rules '(
			(define-counter "enviado-por" [(current "EMISOR")]
               (and 
               (= (current "EMISOR") (past "EMISOR"))
               (includes? (past "tema") (current "tema"))))
             
             (define-counter "Ticket-Contador" [] true)
               
             (define-counter "Ticket-Contador-Rojo" []
               (starts-with? (current "ESTADIO") "R"))
             
             (define-counter "Ticket-Contador-Emisor" [(current "EMISOR")] true)
             
             (define-signal {"Ticket-fraction-Rojo" (/ (counter-value "Ticket-Contador-Rojo" [])
                                                (counter-value "Ticket-Contador" []))}
               true)
             ))
;;;;;;;;;;;;aplicando reglas para strings;;;;;;;;;;;             
(def ticket 
{
"Ticket" 12345
"EMISOR" "AUGUSTO" 
"RECEPTOR" "Propiedad de Arevalo" 
"ESTADIO" "Rojo" 
"tema" "colores"
})
(def ticket_1 
{
"Ticket" 54321
"EMISOR" "AUGUSTO" 
"RECEPTOR" "Propiedad de Arevalo" 
"ESTADIO" "Azul" 
"tema" "colores"
})
(def ticket_2
{
"Ticket" 111
"EMISOR" "Euriterco" 
"RECEPTOR" "Propiedad de Arevalo" 
"ESTADIO" "Verde" 
"tema" "colores"
})

(deftest enviado-tickets
  (testing "contar-tickets enviador por un determinado usuario"
    
    (testing "when repeated"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 ticket)
            st2 (process-data-dropping-signals st1 ticket_1)
            st3 (process-data-dropping-signals st2 ticket_2)
            st4 (process-data-dropping-signals st3 ticket_1)
            st5 (process-data-dropping-signals st4 ticket)
            st6 (process-data-dropping-signals st5 ticket)
            ]
            (println "RESPUESTA estado salida" st6)
        (is (= 4
               (query-counter st6 "enviado-por" ["AUGUSTO"])));;por el tipo de regla reenviado no cuenta el primero
        (is (= 5
               (query-counter st6 "Ticket-Contador-Emisor" ["AUGUSTO"])))       
		(is (= 1
               (query-counter st6 "Ticket-Contador-Emisor" ["Euriterco"])))
               
       ))))     
 (deftest contador-tickets
  (testing "contar-tickets totales"
    (testing "Query counter from initial state"
    (is (= 0
           (query-counter (initialize-processor rules) "Ticket" []))))
    
    (testing "when repeated"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 ticket)
            st2 (process-data-dropping-signals st1 ticket)
            st3 (process-data-dropping-signals st2 ticket)
            st4 (process-data-dropping-signals st3 ticket)
            st5 (process-data-dropping-signals st4 ticket)
            st6 (process-data-dropping-signals st5 ticket)
            ]
            (println "RESPUESTA estado salida" st3)
        (is (= 6
               (query-counter st6 "Ticket-Contador" [])))
               
		(is (= 6
               (query-counter st6 "Ticket-Contador-Rojo" [])))
               
       ))))     
       
 (deftest signal-tickets
  (let [st0 (initialize-processor rules)
        [st1 sg1] (process-data st0 ticket)
        [st2 sg2] (process-data st1 ticket)
        [st3 sg3] (process-data st2 {})
        [st4 sg4] (process-data st3 ticket_1)
        [st5 sg5] (process-data st4 ticket_2)
        
        ]
    ;(println "Estado:***************************************" st5)    
	;(println "sg5" sg5)
    (is (= 0
           (count sg1)))
    (is (= 1
           (count sg2)))
    (is (= 1
           (get (first sg2) "Ticket-fraction-Rojo")))
    (is (= 1
           (count sg3)))
    (is (= 1
           (get (first sg3) "Ticket-fraction-Rojo")
           ));;2/2       
    (is (< 0.49
           (get (first sg5) "Ticket-fraction-Rojo")
           0.51));;2/4
    (is (< 0.65
           (get (first sg4) "Ticket-fraction-Rojo")
           0.67));;2/3
           
      ))          
             
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;             
(deftest t-starts-with?
  (is (clojure.string/starts-with? "abcde" "a"))
  (is (clojure.string/starts-with? "abcde" "ab"))
  (is (clojure.string/starts-with? "abcde" "abc")))

(deftest funcion_con_Strings_00
          (testing "funcion startWith SIN DATO"
                (is (= true (resolver_operacion
                 '(starts-with? "Rojo" "R")))))
                 (testing "funcion startWith SIN DATO"
                (is (= false (resolver_operacion
                 '(starts-with? "Rojo" "Re")))))
                 (testing "funcion startWith SIN DATO"
                (is (= true (resolver_operacion
                 '(starts-with? "Rojo" "Ro")))))
                 (testing "funcion startWith SIN DATO"
                (is (= false (resolver_operacion
                 '(starts-with? "Rojo" "ro")))))
                 (testing "funcion startWith SIN DATO"
                (is (= true (resolver_operacion
                 '(starts-with? "Rojo" ""))))))
(deftest funcion_con_Strings_01
          (testing "funcion startWith SIN DATO"
                (is (= false (resolver_operacion
                 '(starts-with? "Rojo" "r"))))))

(deftest funcion_con_Strings_02
          (testing "funcion startWith? CON DATO"
                (is (= false (resolver_operacion 
                '((starts-with? "Rojo" "a") {:soyUnMapa "ALGO"}))))))

(deftest funcion_con_Strings_03
          (testing "funcion endWith? CON DATO"
                (is (= false (resolver_operacion
                 '((ends-with? "Rojo" "j") {:soyUnMapa "ALGO"}))))))

(deftest funcion_con_Strings_04_1
          (testing "funcion endWith? SIN DATO"
                (is (= false (resolver_operacion 
                '(ends-with? "Rojo" "O"))))))
(deftest funcion_con_Strings_04
          (testing "funcion endWith? SIN DATO"
                (is (= false (resolver_operacion 
                '(ends-with? "Rojo" "j"))))))
(deftest funcion_con_Strings_05
          (testing "funcion includes? SIN DATO"
                (is (= true (resolver_operacion 
                '(includes? "Rojo" "j"))))))

(deftest funcion_con_Strings_06
          (testing "funcion includes? CON DATO"
                (is (= true (resolver_operacion 
                '((includes? "Rojo" "j") 
                {:soyUnMapa "ALGO"}))))))

(deftest funcion_con_Strings_07
          (testing "funcion concat SIN DATO"
                (is (= "RojoVerde" (resolver_operacion
                 '(concat "Rojo" "Verde"))))))
(deftest funcion_con_Strings_08
          (testing "funcion concat CON DATO"
                (is (= "RojoVerde" (resolver_operacion 
                '((concat "Rojo" "Verde")
                {:soyUnMapa "ALGO"}))))))

;;;;;;;;;;;;;;CASO COMPUESTO;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deftest funcion_con_Strings_09
          (testing "funcion para strings combinadas SIN DATO"
                (is (= "AmarilloRojoAzulVerdeAnnio 2018" (resolver_operacion 
                '(concat "Amarillo" "Rojo" "Azul" "Verde" "Annio 2018"))))))

(deftest funcion_con_Strings_10
          (testing "funcion para strings combinadas CON DATO"
                (is (= "AmarilloRojoAzulVerdeAnnio 2018" (resolver_operacion 
                '((concat "Amarillo" "Rojo" "Azul" "Verde" "Annio 2018") 
                {:soyUnMapa "ALGO"}))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deftest funcion_multimetodo-00-test
  (testing "coleccion vacia."
        (is (= '() (resolver_operacion '())))))
(deftest funcion_con_estado
          (testing "coleccion vacia."
                (is (= 8 (resolver_operacion '((+ 5 3) {:soyUnMapa "ALGO"}))))))
(deftest funcion_operar_con_AND-00-test
  (testing "funcionesRecursivas."
        (is (= false (resolver_operacion '(and true true false))))))
(deftest funcion_operar_con_AND-01-test
  (testing "funcionesRecursivas.Operacion plana"
        (is (= false (resolver_operacion '(and true true false))))))
(deftest funcion_operar_aritmetica-00-test
  (testing "funcionesRecursivas."
        (is (= 7 (resolver_operacion '(/ 21 3))))))
(deftest funcion_resolver_operacion-00-test
  (testing "funcionesRecursivas."
        (is (= true (resolver_operacion '(< -10 0))))))
(deftest funcion_resolver_operacion-01-test
    (testing "funcionesRecursivas.")
    (is (= true (resolver_operacion '(= -10 -10)))))
(deftest funcion_resolver_operacion-02-test
    (testing "funcionesRecursivas.")
    (is (= true (resolver_operacion '(>= 10 9 9 8 7 7 7 6 5 4 3 2 1 0 -1000)))))
(deftest funcion_resolver_operacion-03-test
    (testing "funcionesRecursivas.")
    (is (= true (resolver_operacion '(<=  -10 -5 0 10 100 1000)))))
(deftest funcion_resolver_operacion-04-test
  (testing "funcionesRecursivas."
        (is (= false (resolver_operacion '(> 0 0))))))
(deftest funcion_resolver_operacion-05-test
  (testing "funcionesRecursivas."
        (is (= false (resolver_operacion '(> 10 5 7 8 -9))))))

(deftest funcion_compara-00-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= true (resolver_operacion '(< -10 0))))))
(deftest funcion_compara-01-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= false (resolver_operacion '(>= (+ 5 3) (+ 1 2 3 4 5 6 7 8)))))))
(deftest funcion_compara-02-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= false (resolver_operacion '(= (<= (mod 10 6) (mod (+ 3 5)(/ 6 4 3)) ) 0))))))
(deftest funcion_compara-02-bis-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= nil (resolver_operacion '(-(+ (mod 10 6) (mod (/ 6 4 3) 0))))))))
(deftest funcion_compara-03-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= nil (resolver_operacion '(= nil (/ 7 0)))))))
(deftest funcion_compara-04-test
    (testing "funcionesRecursivas aritmEtica."
          (is (= true (resolver_operacion '(= 3 3))))))
(deftest funcion_MOD-00-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= nil (resolver_operacion '(mod 10 0))))))
(deftest funcion_MOD-01-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= 0 (resolver_operacion '(mod 10 5))))))
(deftest funcion_MOD-02-test
    (testing "funcionesRecursivas aritmEtica."
          (is (= 4 (resolver_operacion '(mod 10 6))))))
(deftest funcion_MOD-03-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= 2 (resolver_operacion '(mod (+ 5 3)(/ 9 3)))))))
  (deftest funcion_MOD-04-test
    (testing "funcionesRecursivas aritmEtica."
          (is (= false (resolver_operacion '(= (mod (+ 5 3)(/ 9 3)) (mod (+ 10 3)(- 8 1))))))))

(deftest funcion_AND-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= false (and true false)))))
(deftest funcionesRecursivas-01-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= true (resolver_operacion '(and true true))))))
(deftest funcionesRecursivas-02-test
  (testing "Resultados Ilogicos tras usar #'and."
        (is (= false (resolver_operacion '(and true true false))))))

(deftest funcionesRecursivas-03-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= true (resolver_operacion '(and (+ 5 3) 5 6 7 8))))))

(deftest funcionesRecursivas-04-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."

        (is (= true (resolver_operacion '(or (+ 5 3) 5 6 7 8 (and true (* 2 3 4 5))))))))

(deftest funcionesRecursivas-05-test
  (testing "Argumento expresion anidada"
        (is (= false (resolver_operacion '(or nil (and false (* 2 3 4 5))))))))


(deftest funcionesRecursivas-06-test
    (testing "Argumento expresion retorna nil. caso excepcional"
          (is (= true (resolver_operacion '(or 5 true))))))
(deftest funcionesRecursivas-07-test
  (testing "Argumento expresion retorna nil (or NRO). Caso excepcional.Idem REPL."
        (is (= true (resolver_operacion '(or true 5))))))
(deftest funcionesRecursivas-08-test
  (testing "Argumento expresion anidada."
        (is (= true (resolver_operacion '(or nil nil (and true (* 2 3 4 5))))))))

(deftest funcionesRecursivas-UNO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (resolver_operacion '(/ 3 8))))))

(deftest funcionesRecursivas-DOS-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 2 (resolver_operacion '(- 5 3))))))

(deftest funcionesRecursivas-TRES-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 15 (resolver_operacion '(* 5 3))))))
(deftest funcionesRecursivas-CUATRO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 4 (resolver_operacion '(/ 12 3))))))

(deftest funcionesRecursivas-CINCO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= nil (resolver_operacion '(/ 5 0))))))

(deftest funcionesRecursivas-SEIS-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 7 (resolver_operacion '(quot 21 3))))))


(deftest funcionesDeFuncionesRecursivas-SIETE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (resolver_operacion '(+ 5 3))))))

(deftest funcionesDeFuncionesRecursivas-OCHO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (resolver_operacion '(+ (+ 5 3)))))))

(deftest funcionesDeFuncionesRecursivas-NUEVE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 16 (resolver_operacion '(+ (+ 5 3) (+ 5 3)))))))

(deftest funcionesDeFuncionesRecursivas-DIEZ-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 11 (resolver_operacion '(+ (+ 5 3) (+ 2 1)))))))

(deftest funcionesDeFuncionesRecursivas-ONCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 2 (resolver_operacion '(+ (- 5 3)))))))
(deftest funcionesDeFuncionesRecursivas-DOCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 15 (resolver_operacion '(+ (* 5 3)))))))

(deftest funcionesDeFuncionesRecursivas-TRECE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (resolver_operacion '(* (+ 5 3) (- 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-CATORCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 2/3 (resolver_operacion '(/ (- 5 3) (+ 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-QUINCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 10/3 (resolver_operacion '(* (/ 5 3) (/ 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-DIECISEIS-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 5/6 (resolver_operacion '(/ (/ 5 3) (/ 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-17-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 30 (resolver_operacion '(* (* 5 3) (* 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-18-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= nil (resolver_operacion '(+ (+ 5 3) (/ 2 0)))))))
(deftest funcionesDeFuncionesRecursivas-19-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 24 (resolver_operacion '(+ (+ 5 3) (/ 12 3)(* 4 5)(- 1 9)))))))
(deftest funcionesDeFuncionesRecursivas-20-test
  (testing "Probando not=."
        (is (= true (resolver_operacion '(not= 5 (+ 1 3)))))))
(deftest funcionesDeFuncionesRecursivas-21-test
  (testing "Probando not=."
        (is (= false (resolver_operacion '(not= 5 (+ 1 4)))))))
(deftest funcionesDeFuncionesRecursivas-22-test
  (testing "Probando NOT."
        (is (= false (resolver_operacion '(not true))))))
(deftest funcionesDeFuncionesRecursivas-23-test
    (testing "Probando NOT."
        (is (= true (resolver_operacion '(not false))))))
(deftest funcionesDeFuncionesRecursivas-24-test
    (testing "Probando NOT."
        (is (= false (resolver_operacion '(not (= 5 5)))))))
