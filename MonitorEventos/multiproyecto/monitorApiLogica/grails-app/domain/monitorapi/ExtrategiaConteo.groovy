package monitorapi

class ExtrategiaConteo {
  String datoAcontar
  String arg

  public ExtrategiaConteo (String datoAcontar){
    this.datoAcontar = datoAcontar
    arg = '[]'
  }

  public ExtrategiaConteo (String datoAcontar, String arg){
    this.datoAcontar = datoAcontar
    this.arg = arg
  }

  public int tomarDatos (MotorDeDatos baseDeDatos){
    return baseDeDatos.tomarContador(datoAcontar, arg)
  }

  static constraints = {
  }
}
