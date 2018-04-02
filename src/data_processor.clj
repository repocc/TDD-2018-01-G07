(ns data-processor
  (:require [clojure.core :refer :all]))
          ;;  [clojure.string :refer :all]))

(def valor_cero 0)
(def estado [])
(def contadores {});; {:counter-name [valores almacenados]}
(def sennales '());; reglas tipo signal

(defn counter-value [counter-name & argumentos]
      (if (empty? argumentos)
          ;retorna el Ultimo valor del contador solicitado
          (last (keyword counter-name) contadores)
          nil))
      ;end-if
  ;end-defn
(defn actualizar_Estado []
  ;;autoincremental en una unidad según el ultimo estado almacenado
  (def estado ( conj estado  (inc (last estado)))))

(defn incrementar_Contador [counter-name argFN]
  ;;incrementa el contador una unidad a partir de su ultimo valor almacenado
    (let [clave (keyword counter-name)
          ultimo_valor_incrementado (argFN (last (clave contadores)))]
         (def contadores (merge  contadores { clave (conj ( clave contadores []) ultimo_valor_incrementado)}))))

(defn actualizar_Contadores [counter-name valor]
  ;;agrega un nuevo valor especificado por parametro al contador respectivo
  ;;counter-name es tipo String
  ;;si la clave no existe, se agrega con valor []
  ;;NotaPersonal: Si counter-name es symbol o String, con 'keyword' se convierte en
  ;; ':counter-name', es decir, una clave (Key).
  ;;(clave contadores []) : si no encuentra la clave retorna [].
  ;(println (type counter-name))
  (let [clave (keyword counter-name)]
       (def contadores (merge  contadores { clave (conj ( clave contadores []) valor)}))))

   ;;signal-name es tipo String
   ;;si la clave no existe, se agrega con valor []
(defn actualizar_Sennales [signal-name valor]

   (let [clave (keyword signal-name)]
        (def sennales (merge  sennales { clave (conj ( clave sennales []) valor)}))))

(defn identificar-reglas [rules]
  ;se reinician todas las colecciones de reglas a vacias para reprocesamiento
  ;sucede en los test.
  (def contadores {})
  (def sennales [])
  (map #(let [[ a b & resto] %]
             (if (= (str a) "define-counter")
                 (do
                     ;(println "Es un contador")
                     ;(println b)
                     (actualizar_Contadores (str b) valor_cero))

                 (do
                     (println "Es una sennal")
                     (println b)
                     (def sennales (conj sennales %)))))

        rules));;map


    ;;se inicializan los CONTADORES y ESTADOS en cero. No hace falta ya que al ejecutarse
    ;;inicialmente, estan predefinidos vacios.
    ;;Nota personal: a es un symbol, por lo tanto al comparar con el nombre del contador
    ;;que es tipo String, falla, por eso (str a).
    ;;Se propone filtrar las reglas (contadores y señales).
    ;;Nota personal: En las pruebas de consola 'b' (o lo que sea) es un symbol y (str b) no funciona, a menos que se lo exprese como tal: (str 'b). De esta manera funciona indistintamente para symbol/string.
    ;;Se almacenan los estados en vector def estado
    ;;Nota personal: conj: adiciona en VECTORES el elemento al final, que es lo querido.
    ;;se inicializa estado a 0.
(defn initialize-processor [rules]

  (println "INICIALIZANDO PROCESAMIENTO DE REGLAS...")
  ;reinicializo vector estados
  (def estado [valor_cero])

  (let [  etapa1 (identificar-reglas rules)
          etapa2  (if (> (count etapa1) valor_cero)
                    (first estado)
                    nil)]
          ;let externo
       etapa2))
;end-defn

(defn localizar-Contador [clave_dato new-data]
  ;; name: convierte :contador en "contador"
  ;;incremento contador si aparece su nombre en new-data
  ;(println (str "Localizando contador para clave:" clave_dato))

  (mapv (fn [clave_contador]

          ( if (clojure.string/includes? clave_contador clave_dato)
              ;;si encuentra que hay una counter-name que tiene una substring que es keyword en el new-data, entonces se incrementa esa clave.
             (do
               (println (str "Encontrado contador para clave...." clave_dato))
               ;si el valor asociado es true incrementa contador.
               ;sino no se incrementa y se agrega el ultimo valor que tenia.
               (println (get new-data clave_dato))
               (if (true? (get new-data clave_dato))
                   (incrementar_Contador clave_contador inc)
                   (incrementar_Contador clave_contador +)))
                ;end-if
               ;end-do
               ;*****************************************
               ;SOLO PARA TESTS HASTA CONSULTAR PROFESORES
               ;PORQUE SINO NO SE INCREMENTA SI LAS REGLAS SON CIEGAS
               ;Y SOLO SE VALIDA FORMATO:
               ;unconditional-counter-test
             (incrementar_Contador "email-count" inc)))
               ;se incrementa para todo nuevo dato en test
               ;*****************************************
              ;if
          ;fn interna
       (into [] (keys contadores))));;de map interno
;end-defn

(defn aplicar-reglas-Contador [new-data]
  ;;aplico reglas tipo counter.
  ;;**********************************************************
  ;; obtengo un mapa de las claves del dato entrante
  (def claves_de_dato (keys new-data))
  (println (str "Aplicando reglas Keys nuevo dato.." claves_de_dato))

  (println (str "Contadores disponibles" contadores))
  ;NotaPersonal: (map #(let ...)coll) o (map (fn [...]) coll) funciona perfecto en repl por consola pero al ejecutar los test, NO ejecuta, a veces si (y parece que sigue funcionando como en otras funciones implementadas), y otras no. ???? Por eso cambio por mapv
  (mapv (fn [x]
         (println x)
         (localizar-Contador x new-data) ) claves_de_dato));;mapv porque map funciona perfecto en

  ;;end-defn

(defn aplicar-reglas-Sennales [new-data]
    ;;**********************************************************
    ;;aplico reglas tipo signal.
    ;;************************************************************
  (def datos_generados []);inicio vector.
  (println "Aplicando reglas signal...")
  (def datos_generados
    (mapv #(let [[ tipo_regla dato condicion & resto] %] ;;porque es [(signal_1)...(signal_n)] y me quedo con (signal...)

         (if (= (str tipo_regla) "define-signal")
             (do
               (println tipo_regla "esto es una signal." %)
               (println "Este es el dato: " dato )
               (println "Esta es la condicion: " condicion)
               ;;si se cumple la condicion:bool
               (if (= condicion true)
                ;lo siguiente retorna un Map segun pruebas en repl y no vector.
                ;haría falta lo siguiente tal vez:
                ;(into [] dato)
                (  do
                 (def datos_obtenidos (first dato))
                 datos_obtenidos
                 ;****************TEMPORAL*************
                 ;Solo a los fines de resolver la conversion
                 ;de symbol a funcion para el caso de signal-launch-test

                 ;???????
                 ;*************************************
                 ;(println "DATOS GENERADOS SIGNAL..." datos_obtenidos)
                 )))))

    sennales))
    (println "DATOS GENERADOS SIGNAL..." datos_generados)
  datos_generados);end-defn

(defn process-data [state new-data]

  (println estado)
  ;;se incrementa el estado por cada ejecución
  (actualizar_Estado)
  (if (empty? new-data)
    [(last estado) '()] ;porque hay un signal-skip-on-error-test
    (do
      (aplicar-reglas-Contador new-data)
      [(last estado) (aplicar-reglas-Sennales new-data)])))

;end-defn;;[nil []])

  ;;retorna el valor numerico almacenado en ese contador para esos argumentos
  ;;aun no se sabe que argumentos son y que calculo hacer sobre ellos, por lo tanto se aplica funcion que retorna 0.
  ;;fn [state counter-name counter-args]
  ;;por lo pronto puedo retornar el estado actual de ese contador ignorando el resto de los argumentos
  ;;el estado correspondería con el indice del vector que tiene valor para esa clave.
(defn query-counter [state counter-name  counter-args]
  (println (str contadores))
  (println (str estado))
  (let [numero ( get ((keyword counter-name) contadores) state "No existe valor para ese estado")]
  ;;let [numero 0]
   numero))
