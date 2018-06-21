package fiuba.materia7510.aplicacion.proveedor

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class EstadoServiceSpec extends Specification {

    EstadoService estadoService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Estado(...).save(flush: true, failOnError: true)
        //new Estado(...).save(flush: true, failOnError: true)
        //Estado estado = new Estado(...).save(flush: true, failOnError: true)
        //new Estado(...).save(flush: true, failOnError: true)
        //new Estado(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //estado.id
    }

    void "test get"() {
        setupData()

        expect:
        estadoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Estado> estadoList = estadoService.list(max: 2, offset: 2)

        then:
        estadoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        estadoService.count() == 5
    }

    void "test delete"() {
        Long estadoId = setupData()

        expect:
        estadoService.count() == 5

        when:
        estadoService.delete(estadoId)
        sessionFactory.currentSession.flush()

        then:
        estadoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Estado estado = new Estado()
        estadoService.save(estado)

        then:
        estado.id != null
    }
}
