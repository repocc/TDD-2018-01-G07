package monitorapi

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class InstrumentoServiceSpec extends Specification {

    InstrumentoService instrumentoService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Instrumento(...).save(flush: true, failOnError: true)
        //new Instrumento(...).save(flush: true, failOnError: true)
        //Instrumento instrumento = new Instrumento(...).save(flush: true, failOnError: true)
        //new Instrumento(...).save(flush: true, failOnError: true)
        //new Instrumento(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //instrumento.id
    }

    void "test get"() {
        setupData()

        expect:
        instrumentoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Instrumento> instrumentoList = instrumentoService.list(max: 2, offset: 2)

        then:
        instrumentoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        instrumentoService.count() == 5
    }

    void "test delete"() {
        Long instrumentoId = setupData()

        expect:
        instrumentoService.count() == 5

        when:
        instrumentoService.delete(instrumentoId)
        sessionFactory.currentSession.flush()

        then:
        instrumentoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Instrumento instrumento = new Instrumento()
        instrumentoService.save(instrumento)

        then:
        instrumento.id != null
    }
}
