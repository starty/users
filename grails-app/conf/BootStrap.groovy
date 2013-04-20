import grails.converters.JSON
import users.User
import UserMarshaller

class BootStrap {

    def init = { servletContext ->

        JSON.registerObjectMarshaller(User) { UserMarshaller.marshall(it) }
    }
}
