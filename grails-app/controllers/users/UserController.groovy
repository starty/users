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

        User newUser = new User()
        newUser.email = data.email
        newUser.countryId = data.country_id
        newUser.password = data.password
        newUser.firstName = data.first_name
        newUser.lastName = data.last_name
        newUser.signupDate = new Date()
        newUser.lastLoginDate = newUser.signupDate

        if (newUser.hasErrors()) {
            response.status = 400
            render helperService.renderError("User has errors", "403")
        }

        response.status = 201
        render newUser.signupDate
        return
    }

    def updateUser() {

    }
}
