package users

import org.grails.datastore.mapping.query.Restrictions

class UserService {

    def getUserById(Integer userId) {
        return User.findByUserId(userId)
    }

    def validateMandatoryFields(def data) {
        def missingParameters = []
        if(!data.email) {
            missingParameters.add("email")
        }

        if(!data.country_id) {
            missingParameters.add("country_id")
        }

        if(!data.password) {
            missingParameters.add("password")
        }

        if(!data.first_name) {
            missingParameters.add("first_name")
        }

        if(!data.last_name) {
            missingParameters.add("last_name")
        }

        return missingParameters
    }

    def createUser(def data) {
        User newUser = new User()
        newUser.email = data.email
        newUser.countryId = data.country_id
        newUser.password = data.password
        newUser.firstName = data.first_name
        newUser.lastName = data.last_name
        newUser.signupDate = new Date()
        newUser.lastLoginDate = newUser.signupDate

        newUser.validate()
        return newUser
    }

    def saveUser(User user) {
        return user.save(flush: true)
    }
}
