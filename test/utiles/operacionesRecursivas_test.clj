(ns utiles.operacionesRecursivas-test
  (:require [clojure.test :refer :all]
            [utiles.operacionesRecursivas :refer :all]))
(def test1 '(+ 5 3))
(def test2  '(5 3))

(deftest funcion_multimetodo-00-test
  (testing "coleccion vacia."
        (is (= '() (resolver_operacion '())))))


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
        (is (= 8 (resolver_operacion test1)))))

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
