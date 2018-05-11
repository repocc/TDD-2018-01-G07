package monitorapi

class Tablero {
  int mockGrails
  String nombre
  List<Instrumento> aparatos
  MotorDeDatos datos

  public void agregarInstrumento(Instrumento nuevoInsturmento){
    datos.agragarListener(nuevoInsturmento)
    aparatos.add(nuevoInsturmento)
  }

  public void texxt(){
    datos.tomarDatos()
  }
  static constraints = {
      nombre display: true
      mockGrails display: false
  }
}
