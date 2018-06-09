package monitorapi

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class TableroServiceSpec extends Specification {

    TableroService tableroService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Tablero(...).save(flush: true, failOnError: true)
        //new Tablero(...).save(flush: true, failOnError: true)
        //Tablero tablero = new Tablero(...).save(flush: true, failOnError: true)
        //new Tablero(...).save(flush: true, failOnError: true)
        //new Tablero(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tablero.id
    }

    void "test get"() {
        setupData()

        expect:
        tableroService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Tablero> tableroList = tableroService.list(max: 2, offset: 2)

        then:
        tableroList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tableroService.count() == 5
    }

    void "test delete"() {
        Long tableroId = setupData()

        expect:
        tableroService.count() == 5

        when:
        tableroService.delete(tableroId)
        sessionFactory.currentSession.flush()

        then:
        tableroService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Tablero tablero = new Tablero()
        tableroService.save(tablero)

        then:
        tablero.id != null
    }
}
