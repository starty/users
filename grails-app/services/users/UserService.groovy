package users

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
}
