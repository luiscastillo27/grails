package aprender

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class UsuariosServiceSpec extends Specification {

    UsuariosService usuariosService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Usuarios(...).save(flush: true, failOnError: true)
        //new Usuarios(...).save(flush: true, failOnError: true)
        //Usuarios usuarios = new Usuarios(...).save(flush: true, failOnError: true)
        //new Usuarios(...).save(flush: true, failOnError: true)
        //new Usuarios(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //usuarios.id
    }

    void "test get"() {
        setupData()

        expect:
        usuariosService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Usuarios> usuariosList = usuariosService.list(max: 2, offset: 2)

        then:
        usuariosList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        usuariosService.count() == 5
    }

    void "test delete"() {
        Long usuariosId = setupData()

        expect:
        usuariosService.count() == 5

        when:
        usuariosService.delete(usuariosId)
        sessionFactory.currentSession.flush()

        then:
        usuariosService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Usuarios usuarios = new Usuarios()
        usuariosService.save(usuarios)

        then:
        usuarios.id != null
    }
}
