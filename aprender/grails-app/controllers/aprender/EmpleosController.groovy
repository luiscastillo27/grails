package aprender

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class EmpleosController {

    EmpleosService empleosService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond empleosService.list(params), model:[empleosCount: empleosService.count()]
    }

    def show(Long id) {
        respond empleosService.get(id)
    }

    def create() {
        respond new Empleos(params)
    }

    def save(Empleos empleos) {
        if (empleos == null) {
            notFound()
            return
        }

        try {
            empleosService.save(empleos)
        } catch (ValidationException e) {
            respond empleos.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'empleos.label', default: 'Empleos'), empleos.id])
                redirect empleos
            }
            '*' { respond empleos, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond empleosService.get(id)
    }

    def update(Empleos empleos) {
        if (empleos == null) {
            notFound()
            return
        }

        try {
            empleosService.save(empleos)
        } catch (ValidationException e) {
            respond empleos.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'empleos.label', default: 'Empleos'), empleos.id])
                redirect empleos
            }
            '*'{ respond empleos, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        empleosService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'empleos.label', default: 'Empleos'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'empleos.label', default: 'Empleos'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
