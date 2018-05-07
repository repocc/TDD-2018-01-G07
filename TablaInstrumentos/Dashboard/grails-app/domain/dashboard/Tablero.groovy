package dashboard

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
      mockGrails++
    }
    static constraints = {
				nombre display: true
    }
}
