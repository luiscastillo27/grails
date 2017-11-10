package aprender

class Usuarios {

	Date fechaNacimiento
	String nombre
	String telefono
	int edad
	static hasMany = [empleos:Empleos]
    static constraints = {

    }

}
