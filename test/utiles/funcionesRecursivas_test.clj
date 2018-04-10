(ns utiles.funcionesRecursivas-test
  (:require [clojure.test :refer :all]
            [utiles.funcionesRecursivas :refer :all]))
(def test1 '(+ 5 3))
(def test2  '(5 3))

(deftest funcion_operar_con_AND-00-test
  (testing "funcionesRecursivas."
        (is (= false (operar_con_AND '(true true false))))))
(deftest funcion_operar_con_AND-01-test
  (testing "funcionesRecursivas.Operacion plana"
        (is (= false (operar_con_AND true true false)))))
(deftest funcion_operar_aritmetica-00-test
  (testing "funcionesRecursivas."
        (is (= 7 (operacion_aritmetica /  34  '(21 3))))))
(deftest funcion_operar_comparando-00-test
  (testing "funcionesRecursivas."
        (is (= true (operar_comparando <  34  '(-10 0))))))
(deftest funcion_operar_comparando-01-test
    (testing "funcionesRecursivas."
    (is (= true (operar_comparando =  34  '(-10 -10 ))))))
(deftest funcion_operar_comparando-02-test
    (testing "funcionesRecursivas."
    (is (= true (operar_comparando >=  34  '(10 9 9 8 7 7 76 5 4 3 2 1 0 -1000))))))
(deftest funcion_operar_comparando-03-test
    (testing "funcionesRecursivas."
    (is (= true (operar_comparando <=  34  '(-10 -5 0 10 100 1000))))))
(deftest funcion_operar_comparando-04-test
  (testing "funcionesRecursivas."
        (is (= false (operar_comparando >  34  '(0 0))))))
(deftest funcion_operar_comparando-05-test
  (testing "funcionesRecursivas."
        (is (= false (operar_comparando >  34  '(10 5 7 8 -9))))))

(deftest funcion_compara-00-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= true (f5 '(< -10 0))))))
(deftest funcion_compara-01-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= false (f5 '(>= (+ 5 3) (+ 1 2 3 4 5 6 7 8)))))))
(deftest funcion_compara-02-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= false (f5 '(= (<= (mod 10 6) (mod (+ 3 5)(/ 6 4 3)) ) 0))))))
(deftest funcion_compara-03-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= true (f5 '(= nil (/ 7 0)))))))
(deftest funcion_compara-04-test
    (testing "funcionesRecursivas aritmEtica."
          (is (= true (f5 '(= 3 3))))))
(deftest funcion_MOD-00-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= nil (f5 '(mod 10 0))))))
(deftest funcion_MOD-01-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= 0 (f5 '(mod 10 5))))))
(deftest funcion_MOD-02-test
    (testing "funcionesRecursivas aritmEtica."
          (is (= 4 (f5 '(mod 10 6))))))
(deftest funcion_MOD-03-test
  (testing "funcionesRecursivas aritmEtica."
        (is (= 2 (f5 '(mod (+ 5 3)(/ 9 3)))))))
  (deftest funcion_MOD-04-test
    (testing "funcionesRecursivas aritmEtica."
          (is (= false (f5 '(= (mod (+ 5 3)(/ 9 3)) (mod (+ 10 3)(- 8 1))))))))

(deftest funcion_AND-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= false (first(convertAND true false))))))
(deftest funcionesRecursivas-01-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= true (f5 '(and true true))))))
(deftest funcionesRecursivas-02-test
  (testing "Resultados Ilogicos tras usar #'and."
        (is (= false (f5 '(and true true false ))))))

(deftest funcionesRecursivas-03-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= true (f5 '(and (+ 5 3) 5 6 7 8))))))

(deftest funcionesRecursivas-04-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."

        (is (= nil (f5 '(or (+ 5 3) 5 6 7 8 (and true (* 2 3 4 5))))))))

(deftest funcionesRecursivas-05-test
  (testing "Argumento expresion anidada"
        (is (= nil (f5 '(or 5 (and true (* 2 3 4 5))))))))


(deftest funcionesRecursivas-06-test
    (testing "Argumento expresion retorna nil. caso excepcional"
          (is (= nil (f5 '(or 5 true))))))
(deftest funcionesRecursivas-07-test
  (testing "Argumento expresion retorna nil (or NRO). Caso excepcional.Idem REPL."
        (is (= nil (f5 '(or true 5))))))
(deftest funcionesRecursivas-08-test
  (testing "Argumento expresion anidada."
        (is (= nil (f5 '(or true 5 (and true (* 2 3 4 5))))))))

(deftest funcionesRecursivas-UNO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (f4 test1)))))

(deftest funcionesRecursivas-DOS-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 2 (f4 '(- 5 3))))))

(deftest funcionesRecursivas-TRES-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 15 (f4 '(* 5 3))))))
(deftest funcionesRecursivas-CUATRO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 4 (f4 '(/ 12 3))))))

(deftest funcionesRecursivas-CINCO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= nil (f4 '(/ 5 0))))))

(deftest funcionesRecursivas-SEIS-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 7 (f4 '(quot 21 3))))))


(deftest funcionesDeFuncionesRecursivas-SIETE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (f5 '(+ 5 3))))))

(deftest funcionesDeFuncionesRecursivas-OCHO-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (f5 '(+ (+ 5 3)))))))

(deftest funcionesDeFuncionesRecursivas-NUEVE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 16 (f5 '(+ (+ 5 3) (+ 5 3)))))))

(deftest funcionesDeFuncionesRecursivas-DIEZ-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 11 (f5 '(+ (+ 5 3) (+ 2 1)))))))

(deftest funcionesDeFuncionesRecursivas-ONCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 2 (f5 '(+ (- 5 3)))))))
(deftest funcionesDeFuncionesRecursivas-DOCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 15 (f5 '(+ (* 5 3)))))))

(deftest funcionesDeFuncionesRecursivas-TRECE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 8 (f5 '(* (+ 5 3) (- 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-CATORCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 2/3 (f5 '(/ (- 5 3) (+ 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-QUINCE-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 10/3 (f5 '(* (/ 5 3) (/ 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-DIECISEIS-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 5/6 (f5 '(/ (/ 5 3) (/ 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-17-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 30 (f5 '(* (* 5 3) (* 2 1)))))))
(deftest funcionesDeFuncionesRecursivas-18-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= nil (f5 '(+ (+ 5 3) (/ 2 0)))))))
(deftest funcionesDeFuncionesRecursivas-19-test
  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
        (is (= 24 (f5 '(+ (+ 5 3) (/ 12 3)(* 4 5)(- 1 9 )))))))
;(deftest funcionesRecursivas-simple-test
;  (testing "Argumento sencillo a funcionesRecursivas aritmEtica."
;        (is (= 0 (sum test2)))))
