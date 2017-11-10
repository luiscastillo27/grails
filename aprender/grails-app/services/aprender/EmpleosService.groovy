package aprender

import grails.gorm.services.Service

@Service(Empleos)
interface EmpleosService {

    Empleos get(Serializable id)

    List<Empleos> list(Map args)

    Long count()

    void delete(Serializable id)

    Empleos save(Empleos empleos)

}