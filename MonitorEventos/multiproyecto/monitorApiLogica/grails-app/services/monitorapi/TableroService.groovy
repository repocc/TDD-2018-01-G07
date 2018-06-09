package monitorapi

import grails.gorm.services.Service

@Service(Tablero)
interface TableroService {

    Tablero get(Serializable id)

    List<Tablero> list(Map args)

    Long count()

    void delete(Serializable id)

    Tablero save(Tablero tablero)

}