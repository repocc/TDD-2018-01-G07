package fiuba.materia7510.aplicacion.generador

import grails.gorm.services.Service

@Service(Estadio)
interface EstadioService {

    Estadio get(Serializable id)

    List<Estadio> list(Map args)

    Long count()

    void delete(Serializable id)

    Estadio save(Estadio estadio)

}
