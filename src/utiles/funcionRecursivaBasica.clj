(ns utiles.funcionRecursivaBasica
  (:require [clojure.core :refer :all]))

(declare recursion)
(declare recursion_coll)
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;Nota planteada para colecciones tipo list.
(defn iniciar_recursion
  "Funcion planteada de modo de resolver los casos (casos de colecciones tipo list):
   + - * / < > <= >= = and or."
  [operador x]

  (let [ a  (apply operador(flatten(recursion_coll x))) ]



  a))

(defn recursion
"Función recursiva que retorna una lista anidada de numeros enteros decrecientes en una unidad en cada recusión.
Recibe por parametro un número entero y decrece hasta cero."
   [x]

 (let [a x b () ]
  (if (= a 0)
   a
   ;Nota personal: Evaluar el uso de lazy-seq.
   ;lazy-seq was introduced to make it possible to postpone any computation
   ; until data is needed.
   (list a (recursion(dec a)))
)))

(defn recursion_coll
  "Función recursiva que recibe una colección tipo lista y retorna una coleccion tipo list anidando sucesivamente cada elemento de la colección.Ejemplo: (a(b(c(d(etc)))))"
   [coll]

  (let [a () b  coll]
   (if (empty? b)
    a
    ;Nota personal: Evaluar el uso de lazy-seq.
    ;lazy-seq was introduced to make it possible to postpone any computation
    ; until data is needed.
    ;; the lazy-seq allows us to make a recursive call in a safe way because
    ;; the call does not happen immediately but instead creates a closure.
    (list (first b) (recursion_coll  (rest b) ) )
    )))
