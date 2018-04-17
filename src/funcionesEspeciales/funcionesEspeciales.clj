(ns funcionesEspeciales.funcionesEspeciales
  (:require [clojure.core :refer :all]
    ))

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
