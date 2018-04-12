(ns utiles.funcionRecursivaBasica-test
  (:require [clojure.test :refer :all]
            [utiles.funcionRecursivaBasica :refer :all]))

(deftest a-test
  (testing "FIXME, I fail ARREGLADO."
    (is (= 1 1))))
(deftest recursion-00-test
      (testing "Fix."
        (is (= true (iniciar_recursion > '(10 9 8 7))))))
(deftest recursion-01-test
      (testing "Fix."
        (is (= false (iniciar_recursion > '(10 9 8 8 7))))))
(deftest recursion-02-test
         (testing "Fix."
           (is (= true (iniciar_recursion <= '(1 2 3 5 6 7 8 9))))))
(deftest recursion-03-test
         (testing "Fix."
           (is (= true (iniciar_recursion >= '(8 7 7 6 6 0 -10))))))
(deftest recursion-05-test
         (testing "Fix."
           (is (= false (iniciar_recursion >= '(8 7 7 6 6 100))))))
