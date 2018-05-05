package dashboard

class Instrumento {

    ExtrategiaConteo conteo
    ExtrategiaVisualización visualización

    public Instrumento (){
  		conteo = new ExtrategiaConteo()
      visualización = new ExtrategiaVisualización()
    }

    public Instrumento (ExtrategiaVisualización EV){
  		conteo = new ExtrategiaConteo()
      visualización = EV
    }

    static constraints = {
    }
}
