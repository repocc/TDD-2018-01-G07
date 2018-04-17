(ns definiciones.definiciones
  (:require [clojure.core :refer :all]
   :require [funcionesEspeciales.funcionesEspeciales :refer [operar_con_AND operar_con_OR]]
   ))
   ;*****************TEMPORAL************************************
   (declare counter-value);solo para compilar.Hasta resolver dependencia ciclica.
   (declare current);solo para compilar.Hasta resolver dependencia ciclica.
   (declare past);solo para compilar.Hasta resolver dependencia ciclica.

  ;**********************Diccionarios********************************

  (def diccionario_op_igualdades {'= = 'not= not=})
  (def diccionario_op_logicas {'and #'operar_con_AND 'or #'operar_con_OR})
  (def diccionario_op_comparativas {'< < '> > '<= <= '>= >=})
  (def diccionario_op_aritmeticas {'+ + '- - '* * '/ / 'quot quot})
  (def diccionario_op_NOT {'not not});casos particulares
  (def diccionario_op_MOD {'mod mod});casos particulares
  (def diccionario_op_Func_especiales {'counter-value #'counter-value 'current #'current 'past #'past})
  (def diccionario_op_strings {'concat str 'includes? clojure.string/includes? 'starts-with? clojure.string/starts-with? 'ends-with? clojure.string/ends-with?})
