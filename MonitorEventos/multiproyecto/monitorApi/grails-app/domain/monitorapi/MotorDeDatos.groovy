package monitorapi

//la  idea es una primera version que es a trucado. Luego cuando eso funcione bien puedo pasarlo por mi cuenta a la final

class MotorDeDatos {
	//MotorService motor = new MotorService()
  // def status //(pd no se que tipo de dato es status)
  int moke = 0
  int numero
  String nombre
  List<Instrumento> listeners
  
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
    //status = motor.inicializar_procesador(reglas)
  }

  public void tomarDatos(){
    //status = motor.inicializar_procesador(reglas)
    numero++ //quitar esta linea
    informarCambioEstado()
  }

  public int tomarContador(){
    def nombreContador 	= 'email-count'
    def arg		= '[]'
    //return motor.consultar_contador(status, nombreContador,arg))
    return numero //quitar esta linea
  }

  static constraints = {
    nombre display: true
    moke display: false
  //motor display: false
  }
}
