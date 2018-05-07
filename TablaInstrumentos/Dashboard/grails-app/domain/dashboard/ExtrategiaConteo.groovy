package dashboard

class ExtrategiaConteo {
    DatosInstrumento datos

    public ExtrategiaConteo (){
  		datos = new DatosInstrumento(1)
    }

    public int tomarDatos (MotorDeDatos baseDeDatos){
      return baseDeDatos.tomarContador()
    }

    static constraints = {
    }
}
