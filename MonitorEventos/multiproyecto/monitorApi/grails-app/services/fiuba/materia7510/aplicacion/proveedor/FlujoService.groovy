package fiuba.materia7510.aplicacion.proveedor

import grails.gorm.services.Service

@Service(Flujo)
interface FlujoService {

    Flujo get(Serializable id)

    List<Flujo> list(Map args)

    Long count()

    void delete(Serializable id)

    Flujo save(Flujo flujo)

}
