package tp7510.monitor.motores.motorClojure
import tp7510.monitor.Demo
import tp7510.monitor.Demo.*
import grails.gorm.transactions.Transactional

@Transactional
class MotorService {

    def service_QuienSoy(){
      clj['grails.procesadorDatosTodoEnUno'].quienSoy()
  }

    def service_Process_Data(state, nuevoDato) {
      clj['grails.procesadorDatosTodoEnUno'].process-data(state, nuevoDato)
    }
    def service_Initialize_processor(rules) {
      // clj['procesadorDatosTodoEnUno'].initialize-processor(rules)
      new Demo().demontre(rules)
    }
    def service_Query_counter(state, counter_name, counter_args) {
      clj['grails.procesadorDatosTodoEnUno'].query-counter(state, counter_name, counter_args)
    }

    def service_Mensajear(){//(String msg){
      println "algo pasa aqui"
    //  println msg
    }
}
