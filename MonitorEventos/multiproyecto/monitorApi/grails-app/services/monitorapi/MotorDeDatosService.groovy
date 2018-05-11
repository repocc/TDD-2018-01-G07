package monitorapi

import grails.gorm.services.Service

@Service(MotorDeDatos)
interface MotorDeDatosService {

    MotorDeDatos get(Serializable id)

    List<MotorDeDatos> list(Map args)

    Long count()

    void delete(Serializable id)

    MotorDeDatos save(MotorDeDatos motorDeDatos)

}