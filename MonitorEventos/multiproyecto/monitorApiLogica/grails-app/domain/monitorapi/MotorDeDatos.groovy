package monitorapi
import fiuba.materia7510.aplicacion.MotorService

class MotorDeDatos {
  String status
  int moke = 0
  String nombre
  List<Instrumento> listeners
//revisar
  protected void agragarListener(Instrumento listener) {
      listeners.add(listener)
  }

  protected void informarCambioEstado() {
    for(Instrumento listener : listeners) {
      listener.informar(this)
    }
  }

  public MotorDeDatos(){
    def reglas = '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
    status = MotorService.inicializar_procesador(reglas)
  }

  public nuevasReglas(String reglas){
      status = MotorService.inicializar_procesador(reglas)
  }

  public void tomarDatos(String dato){
    status = MotorService.process_data_dropping_signals(status ,dato)
    informarCambioEstado()
  }

  public int tomarContador(String nombreContador, String arg){
    return MotorService.consultar_contador(status, nombreContador,arg)
  }

  static constraints = {
    nombre display: true
    moke display: false
  	status size: 5..1500
  }
}
