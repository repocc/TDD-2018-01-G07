package monitorapi

import grails.gorm.services.Service

@Service(Instrumento)
interface InstrumentoService {

    Instrumento get(Serializable id)

    List<Instrumento> list(Map args)

    Long count()

    void delete(Serializable id)

    Instrumento save(Instrumento instrumento)

}