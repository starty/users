package users

import grails.converters.JSON
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET


class UserController {

    def helperService
    def userService

    def getUserById(){
        if(!params.userId) {
            response.status = 403 // Forbidden
            render helperService.renderError("Parameter 'userId' is mandatory.", "403")
            return
        }

        def user = userService.getUserById(Integer.parseInt(params.userId))

        if (user) {
            response.status = 200
            render user as JSON
            return
        }
        else{
            response.status = 404 // Forbidden
            render helperService.renderError("User with id "+params.userId+" not found.", "404")
            return
        }
    }

    def createUser() {
        def data = request.JSON
        def missingParameters = userService.validateMandatoryFields(data)

        if (missingParameters) {
            def paramsString = ""
            for (missingParameter in missingParameters) {
                paramsString += missingParameter+", "
            }
            paramsString = paramsString[0..-3]
            response.status = 403 // Forbidden
            if (missingParameters.size() == 1)
                render helperService.renderError("Parameter "+paramsString+" is mandatory.", "403")
            else
                render helperService.renderError("Parameters "+paramsString+" are mandatory.", "403")
            return
        }

        User newUser = userService.createUser(data)

        if (newUser.hasErrors()){
            response.status =  403 // Forbidden
            render helperService.renderError("Error occurred creating user. Please validate input fields.", "403")
        }

        if (userService.saveUser(newUser)) {
            response.status = 201 // Created
            render newUser.signupDate
            return
        } else {
            response.status =  500 // Forbidden
            render helperService.renderError("Unknown error saving user.", "500")
        }
    }

    def updateUser() {
        if(!params.userId) {
            response.status = 403 // Forbidden
            render helperService.renderError("Parameter 'userId' is mandatory.", "403")
            return
        }

        def user = userService.getUserById(Integer.parseInt(params.userId))

        if (user) {
            user = userService.updateUser(user, request.JSON)

            response.status = 200
            render user as JSON
            return
        }
        else{
            response.status = 404 // Forbidden
            render helperService.renderError("User with id "+params.userId+" not found.", "404")
            return
        }
    }

    //TODO: Create multiget method
    def getUsersByIds() {
        if(!params.ids) {
            response.status = 403 // Forbidden
            render helperService.renderError("Parameter 'ids' is mandatory.", "403")
            return
        }

        String[] ids = params.ids.split(",")

        if (!(ids.size() >= 1)) {
            response.status = 403 // Forbidden
            render helperService.renderError("A minimum amount of one id is mandatory.", "403")
            return
        }

        def usersMap = [:]

        ids.each { id ->
            User user = userService.getUserById(Integer.parseInt(id))
            if (user)
                usersMap.put(user.getUserId(), user)
        }

        response.status = 200 // OK
        render usersMap as JSON
        return
    }

    /*
    def test(){
        //192.168.1.144

        def http = new HTTPBuilder('http://192.168.1.144:8090/mockserver/category/1')

        http.request(GET, groovyx.net.http.ContentType.JSON ) { req ->
            headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
            headers.Accept = 'application/json'

            response.success = { resp, reader ->
                assert resp.statusLine.statusCode == 200
                println "Got response: ${resp.statusLine}"
                println "Content-Type: ${resp.headers.'Content-Type'}"
                println reader.text
            }

            response.'404' = {
                println 'Not found'
            }
        }
    }
    */
}
