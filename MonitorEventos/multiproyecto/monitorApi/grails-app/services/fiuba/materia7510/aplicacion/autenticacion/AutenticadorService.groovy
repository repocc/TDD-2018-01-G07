package fiuba.materia7510.aplicacion.autenticacion

import grails.gorm.services.Service

@Service(Autenticador)
interface AutenticadorService {

    Autenticador get(Serializable id)

    List<Autenticador> list(Map args)

    Long count()

    void delete(Serializable id)

    Autenticador save(Autenticador autenticador)

}