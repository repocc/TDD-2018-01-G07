(ns funcionesEspeciales.funcionesEspeciales)

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
