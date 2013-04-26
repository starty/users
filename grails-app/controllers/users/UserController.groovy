package users

import grails.converters.JSON

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

    //TODO: Create multiget method

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
}
