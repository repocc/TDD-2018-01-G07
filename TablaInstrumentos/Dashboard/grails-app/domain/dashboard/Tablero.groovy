package dashboard

class Tablero {
  	String nombre
	  List<Instrumento> aparatos

    void agregarInstrumento(Instrumento nuevoInsturmento){
      aparatos.add(nuevoInsturmento)
    }

    static constraints = {
				nombre display: true
    }
}
