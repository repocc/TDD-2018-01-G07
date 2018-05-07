package dashboard

class BaseDeDatos {
    int mockGrails
    String nombre

    public void texxt(){
      mockGrails++
    }
    static constraints = {
				nombre display: true
    }

}
