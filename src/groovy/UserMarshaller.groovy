import users.User

class UserMarshaller {

    static marshall(User user){
        def userMap = [:]
        def identificationMap = [:]
        def locationMap = [:]
        def phoneMap = [:]

        userMap.user_id = user.userId
        userMap.email = user.email
        userMap.country_id = user.countryId
        userMap.first_name = user.firstName
        userMap.last_name = user.lastName
        userMap.signup_date = user.signupDate
        userMap.last_login_date = user.lastLoginDate
        userMap.status = user.status
        userMap.user_type = user.userType

        identificationMap.type = user.identificationType
        identificationMap.number = user.identificationNumber
        userMap.identification = identificationMap

        locationMap.state = user.state
        locationMap.city = user.city
        locationMap.neighborhood = user.neighborhood
        locationMap.address = user.address
        locationMap.zip_code = user.zipCode
        userMap.location = locationMap

        phoneMap.area_code = user.areaCode
        phoneMap.number = user.phoneNumber
        phoneMap.extension = user.phoneExtension
        phoneMap.verified = user.phoneVerified
        userMap.phone = phoneMap

        return userMap
    }
}
