package monitorapi

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MotorDeDatosServiceSpec extends Specification {

    MotorDeDatosService motorDeDatosService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MotorDeDatos(...).save(flush: true, failOnError: true)
        //new MotorDeDatos(...).save(flush: true, failOnError: true)
        //MotorDeDatos motorDeDatos = new MotorDeDatos(...).save(flush: true, failOnError: true)
        //new MotorDeDatos(...).save(flush: true, failOnError: true)
        //new MotorDeDatos(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //motorDeDatos.id
    }

    void "test get"() {
        setupData()

        expect:
        motorDeDatosService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MotorDeDatos> motorDeDatosList = motorDeDatosService.list(max: 2, offset: 2)

        then:
        motorDeDatosList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        motorDeDatosService.count() == 5
    }

    void "test delete"() {
        Long motorDeDatosId = setupData()

        expect:
        motorDeDatosService.count() == 5

        when:
        motorDeDatosService.delete(motorDeDatosId)
        sessionFactory.currentSession.flush()

        then:
        motorDeDatosService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MotorDeDatos motorDeDatos = new MotorDeDatos()
        motorDeDatosService.save(motorDeDatos)

        then:
        motorDeDatos.id != null
    }
}
