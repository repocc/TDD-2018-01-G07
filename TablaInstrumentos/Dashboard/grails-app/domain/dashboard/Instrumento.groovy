package dashboard

class Instrumento {
    int moke = 0
    ExtrategiaConteo conteo
    ExtrategiaVisualización visualización
    String falsoGrafico

    public Instrumento (){
  		conteo = new ExtrategiaConteo()
      visualización = new ExtrategiaVisualización()
      falsoGrafico = "nada"
    }

    public Instrumento (ExtrategiaVisualización EV){
  		conteo = new ExtrategiaConteo()
      visualización = EV
      falsoGrafico = "nada"
    }

    public void informar (MotorDeDatos baseDeDatos){
      falsoGrafico = (visualización.graficar(conteo.tomarDatos(baseDeDatos)))
    }

    static constraints = {
        moke display: false
    }
}
