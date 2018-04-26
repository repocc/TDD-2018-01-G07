package tp7510.monitor.motores.motorClojure

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class MotorServiceSpec extends Specification implements ServiceUnitTest<MotorService>{
    MotorService procesador = new MotorService()

    def rules= ['((define-counter "email-count" []             true)(define-counter "spam-count" [] (current "spam"))                   (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])                                                    (counter-value "email-count" []))} true)                   (define-counter "spam-important-table" [(current "spam")                                                           (current "important")] true))']

    def setup() {
      println "INICIANDO SETUP..."
  /*    println procesador
      if (procesador != null){
        println "ES NO NULL EL PROCESADOR."

        procesador.service_Mensajear()
      }
      else
        {
      procesador.service_Mensajear("Empezando testing Motor-CLOJURE...")
      procesador.service_QuienSoy()
    }
    */
    }

    def cleanup() {
    }

    def process_data_dropping_signals (state, nuevo_data){

      def rta = procesador.ProcessData(state, nuevo_data).get(0)

    rta}

      //************************************************************
      //deftest initial-state-test
  void "acceptance-test_01"() {
        expect: "Query counter from initial state"
        0 == procesador.service_Query_counter(  (procesador.service_Initialize_processor (rules.get(0))),
    "spam", [])
  }
  void "acceptance-test_02"() {

    def st0 = procesador.service_Initialize_processor (rules.get(0))
    def st1 = process_data_dropping_signals (st0, {"spam" true})
    def st2 = process_data_dropping_signals (st1, {"spam" true})
    expect:" unconditional-counter-test"
    2 == procesador.service_Query_counter(st2, "email-count", [])
  }
      //(deftest conditional-counter-test
  void "acceptance-test_03"() {

    def st0 = procesador.service_Initialize_processor (rules.get(0))
    def st1 = process_data_dropping_signals (st0, {"spam" true})
    def st2 = process_data_dropping_signals (st1, {"spam" true})
    def st3 = process_data_dropping_signals (st2,{"spam" true})
    expect: "Count incoming data by current condition .when repeated"
    3 == procesador.service_Query_counter( st3 ,"spam-count" [])
  }
  void "acceptance-test_04"() {

    def  st0 = procesador.service_Initialize_processor (rules.get(0))
    def  st1 = process_data_dropping_signals (st0, {"spam" true, "noise" 1})
    def  st2 = process_data_dropping_signals (st1, {"spam" true, "noise" 2})
    def  st3 = process_data_dropping_signals  (st2, {"spam" true, "noise" 3})
      expect: "when ignored field varies"
      3 == procesador.serviceMethod.query-counter( st3, "spam-count", [])

  }
  void "acceptance-test_05"() {

    def  st0 = procesador.service_Initialize_processor (rules.get(0))
    def  st1 = process_data_dropping_signals (st0, {"spam" true})
    def  st2 = process_data_dropping_signals (st1, {"spam" false})
    def  st3 = process_data_dropping_signals (st2 ,{"spam" true})
    expect: "when considered field varies"
    2 ==  procesador.serviceMethod.query-counter(st3, "spam-count", [])

  }
  void "acceptance-test_06"() {

      def st0 = procesador.service_Initialize_processor (rules.get(0))
      def  st1 = process_data_dropping_signals (st0, {"spam" true, "important" true})
      def  st2 = process_data_dropping_signals (st1, {"spam" true, "important" false})
      def  st3 = process_data_dropping_signals (st2, {"spam" true, "important" false})
      def  st4 = process_data_dropping_signals (st3, {"spam" false, "important" true})
      def  st5 = process_data_dropping_signals (st4, {"spam" false, "important" true})
      def  st6 = process_data_dropping_signals (st5, {"spam" false, "important" true})
      def  st7 = process_data_dropping_signals (st6, {"spam" false, "important" false})
      def  st8 = process_data_dropping_signals (st7, {"spam" false, "important" false})
      def  st9 = process_data_dropping_signals (st8, {"spam" false, "important" false})
      def end_state = process_data_dropping_signals (st9, {"spam" false, "important" false})

       expect: "contingency-table-counter-test.Espero 1"
      1 == procesador.service_Query_counter(end_state, "spam-important-table", [true, true])

      and: "Espero 2."
      2 == procesador.service_Query_counter(end_state, "spam-important-table", [true, false])

                   and: "Espero 3."

      3 == procesador.service_Query_counter(end_state, "spam-important-table", [false, true])
                     and: "Espero 4."

      4 == procesador.service_Query_counter(end_state, "spam-important-table", [false, false])

  }
  void "acceptance-test_07"() {

    def st0 = procesador.service_Initialize_processor (rules.get(0))
    def sg1 = [:]
    def l1 =[st1, sg1]
      l1 = procesador.service_Process_Data(st0, {})
        expect:  "signal-skip-on-error-test"
         [:] == sg1
  }
  void "acceptance-test_08"() {
     def sg1 = [:]
     def sg2 = [:]
     def sg3 = [:]
     def sg4 = [:]
     def sg5 = [:]

     def l1 =[st1, sg1]
     def l2 =[st2, sg2]
     def l3 =[st3, sg3]

     def st0 = procesador.service_Initialize_processor (rules.get(0))

      l1 = procesador.service_Process_Data(st0, {"spam" true})
      l2 = procesador.service_Process_Data (st1, {"spam" false})
      l3 = procesador.service_Process_Data (st2, {})
            expect: "signal-launch-test"
              0 == sg1.size()

              and: "Espero uno."

              1 == sg2.size()
              and: "Espero uno."

              1 == sg2.get(0).get("spam-fraction")
              and: "Espero tamaño 1."

              1 == sg3.size()
              and: "Espero fraccion spam 0.5."

              ((sg3.get(0).get("spam-fraction")  < 0.49)
              &&
              (sg3.get(0).get("spam-fraction") < 0.51))
  }
  void "acceptance-test_09"() {
      def sg1 = [:]
      def sg2 = [:]
      def sg3 = [:]
      def sg4 = [:]
      def sg5 = [:]

      def l1 =[st1, sg1]
      def l2 =[st2, sg2]
      def l3 =[st3, sg3]
      def l4 =[st4, sg4]
      def l5 =[st5, sg5]
      def st0 = procesador.service_Initialize_processor( ['((define-signal {"repeated" (current "value")} (= (current "value") (past "value"))))'].get(0))

      l1 = procesador.service_Process_Data(st0, {"value" 1})
      l2 = procesador.service_Process_Data(st1, {"value" 2})
      l3 = procesador.service_Process_Data(st2, {"value" 1})
      l4 = procesador.service_Process_Data(st3, {"value" 1})
      l5 = procesador.service_Process_Data(st4, {"value" 2})
        expect: "past-value-test"
          0 == sg1.size()
                 and: "past-value-test.size CERO"

          0 == sg2.size()
                 and: "past-value-test.igual sg3"

          sg3 == ["repeated": 1]

                 and: "past-value-test. Igual sg4."

          sg4 =='({"repeated" 1})'
                 s
                 and: "past-value-test.Igual sg5"

           sg5 == '({"repeated" 2})'



        and:"FIN en acceptance-test"
            true == false
    }
    void "test something"() {
        expect:"fix me"
            true == false
    }
}
