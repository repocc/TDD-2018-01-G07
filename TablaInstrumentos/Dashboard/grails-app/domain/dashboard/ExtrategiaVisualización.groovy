package dashboard

class ExtrategiaVisualización {
    int numero

    ExtrategiaVisualización(){
      numero = 1
    }

    public String graficar(int datosNuevos){
      return "Soy un Grafico: " + datosNuevos
    }

    static constraints = {
    }
}
