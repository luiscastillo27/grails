package aprender

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class EmpleosServiceSpec extends Specification {

    EmpleosService empleosService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Empleos(...).save(flush: true, failOnError: true)
        //new Empleos(...).save(flush: true, failOnError: true)
        //Empleos empleos = new Empleos(...).save(flush: true, failOnError: true)
        //new Empleos(...).save(flush: true, failOnError: true)
        //new Empleos(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //empleos.id
    }

    void "test get"() {
        setupData()

        expect:
        empleosService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Empleos> empleosList = empleosService.list(max: 2, offset: 2)

        then:
        empleosList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        empleosService.count() == 5
    }

    void "test delete"() {
        Long empleosId = setupData()

        expect:
        empleosService.count() == 5

        when:
        empleosService.delete(empleosId)
        sessionFactory.currentSession.flush()

        then:
        empleosService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Empleos empleos = new Empleos()
        empleosService.save(empleos)

        then:
        empleos.id != null
    }
}
