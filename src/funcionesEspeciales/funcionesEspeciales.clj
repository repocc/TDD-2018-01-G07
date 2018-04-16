(ns funcionesEspeciales.funcionesEspeciales
  (:require [clojure.core :refer :all]
    :require [definiciones.definiciones :refer :all]))


(defn past [clave]
    (get LaX clave))


(defn current [clave]
    (get newData clave))

(defn counter-value [counter-name counter-args]
    (let [ salidaCV ((keyword counter-name) contadores)]

        (if (= salidaCV nil)
            valor_cero
            (if (= (count (first salidaCV)) valor_cero)
                 (last(last salidaCV))
                 (last(get (last salidaCV) counter-args))
              );end-if
        );end-if
    );end-let
);end-defn
