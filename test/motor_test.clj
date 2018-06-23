(ns motor-test
  (:require [clojure.test :refer :all]
	 [motor :refer :all]
	))

(def rules '((define-counter "email-count" []
               true)
             (define-counter "spam-count" []
               (current "spam"))
             (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])
                                                (counter-value "email-count" []))}
               true)
             (define-counter "spam-important-table" [(current "spam")
                                                     (current "important")]
               true)))
	
	
;;(deftest main-test
;;  (testing "Parametros incorrectos en main."
;;    (is (= nil (-main {"Soy args.""Argumento Enviado.":dato {"spam" true}})))
;;    (is (= nil (-main  "No soy map.")))))

;;(deftest main-01-test
;;  (testing "Parametros CORRECTOS en main."
;;    (is (= true (map? (-main 
;;							{:reglas rules 
;;							:nombreContador "spam-count" 
;;							:dato {"spam" true}}))))
;;    (is (= true (map? (-main 
;;							{:reglas rules 
;;							:nombreContador "email-count" 
;;							:dato {"spam" true}}))))))
