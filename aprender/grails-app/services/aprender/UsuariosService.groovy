package aprender

import grails.gorm.services.Service

@Service(Usuarios)
interface UsuariosService {

    Usuarios get(Serializable id)

    List<Usuarios> list(Map args)

    Long count()

    void delete(Serializable id)

    Usuarios save(Usuarios usuarios)

}