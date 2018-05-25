package tickets

class EstadoTicket {
  String nombre
  List<EstadoTicket> siguientes

    public EstadoTicket(){

    siguientes = []

    }

    static constraints = {
    }
}
