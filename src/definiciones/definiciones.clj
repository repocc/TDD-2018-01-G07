(ns definiciones.definiciones
  (:require [clojure.core :refer :all]
   :require [funcionesEspeciales.funcionesEspeciales :refer :all
   ;[operar_con_AND operar_con_OR counter-value current past]
   ]
   ))
   ;*****************TEMPORAL************************************

 ;(println '((/ (counter-value spam-count []) (counter-value email-count [])) {:estado {:Contadores {:email-count [[] true [1]], :spam-count [[] (current spam) [0]], :spam-important-table [[(current spam) (current important)] true {}]}, :ContadorSteps {}, :Sennales {:spam-fraction [(/ (counter-value spam-count []) (counter-value email-count [])) true]}, :DatosPasados {{spam true} true}}, :dato_pasado nil, :dato_actual {spam false}}))
  ; (println '(counter-value spam-count [] {:estado {:Contadores {:email-count [[] true [3]], :spam-count [[] (current spam) [0]], :spam-important-table [[(current spam) (current important)] true {}]}, :ContadorSteps {}, :Sennales {:spam-fraction [(/ (counter-value spam-count []) (counter-value email-count [])) true]}, :DatosPasados {{spam true} true, {spam false} true}}, :dato_pasado nil, :dato_actual nil}) )

  ;**********************Diccionarios********************************

  (def diccionario_op_igualdades {'= = 'not= not=})
  (def diccionario_op_logicas {'and #'operar_con_AND 'or #'operar_con_OR})
  (def diccionario_op_comparativas {'< < '> > '<= <= '>= >=})
  (def diccionario_op_aritmeticas {'+ + '- - '* * '/ / 'quot quot})
  (def diccionario_op_NOT {'not not});casos particulares
  (def diccionario_op_MOD {'mod mod});casos particulares
  (def diccionario_op_Func_especiales {'counter-value #'counter-value 'current #'current 'past #'past})
  (def diccionario_op_strings {'concat str 'includes? clojure.string/includes? 'starts-with? clojure.string/starts-with? 'ends-with? clojure.string/ends-with?})
