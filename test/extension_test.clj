(ns extension-test
  (:require [clojure.test :refer :all]
            [data-processor :refer :all]))

(defn process-data-dropping-signals [state new-data]
  (first (process-data state new-data)))

(deftest equivalence-test
  (testing "define-counter as special case of define-step-counter"
    (let [st0 (initialize-processor '((define-counter "old" []
                                        true)
                                      (define-step-counter "new" 1 []
                                        true)))
          st1 (process-data-dropping-signals st0 {})
          st2 (process-data-dropping-signals st1 {})]
      (is (= (query-counter st1 "old" [])
             (query-counter st1 "new" [])))
      (is (= (query-counter st2 "old" [])
             (query-counter st2 "new" []))))))

(deftest step-increment-test
  (testing "define-step-counter with variable step"
    (let [st0 (initialize-processor '((define-step-counter "stepper" (current "delta") []
                                        true)))
          st1 (process-data-dropping-signals st0 {"delta" 2})
          st2 (process-data-dropping-signals st1 {"delta" 5.5})]
      (is (= 2
             (query-counter st1 "stepper" [])))
      (is (= 7.5
             (query-counter st2 "stepper" []))))))
