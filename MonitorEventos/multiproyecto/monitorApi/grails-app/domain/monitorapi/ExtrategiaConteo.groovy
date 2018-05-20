package monitorapi

class ExtrategiaConteo {
  String datoAcontar

  public ExtrategiaConteo (String datoAcontar){
    this.datoAcontar = datoAcontar
  }

  public int tomarDatos (MotorDeDatos baseDeDatos){
    return baseDeDatos.tomarContador(datoAcontar)
  }

  static constraints = {
  }
}
