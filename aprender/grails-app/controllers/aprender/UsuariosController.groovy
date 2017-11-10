package aprender

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UsuariosController {

    UsuariosService usuariosService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond usuariosService.list(params), model:[usuariosCount: usuariosService.count()]
    }

    def show(Long id) {
        respond usuariosService.get(id)
    }

    def create() {
        respond new Usuarios(params)
    }

    def save(Usuarios usuarios) {
        if (usuarios == null) {
            notFound()
            return
        }

        try {
            usuariosService.save(usuarios)
        } catch (ValidationException e) {
            respond usuarios.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), usuarios.id])
                redirect usuarios
            }
            '*' { respond usuarios, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond usuariosService.get(id)
    }

    def update(Usuarios usuarios) {
        if (usuarios == null) {
            notFound()
            return
        }

        try {
            usuariosService.save(usuarios)
        } catch (ValidationException e) {
            respond usuarios.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), usuarios.id])
                redirect usuarios
            }
            '*'{ respond usuarios, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        usuariosService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
