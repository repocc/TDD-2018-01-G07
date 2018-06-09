package monitorapi

import fiuba.materia7510.aplicacion.utilidad.TesterBootstrap
class BootStrap {

    
    
    def init = { servletContext ->
		new TesterBootstrap().cargar_Datos()
    
    }
    def destroy = {
    }
}

