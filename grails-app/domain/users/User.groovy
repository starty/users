package users

@grails.validation.Validateable
class User {

    Integer userId
    String email
    String countryId
    String password
    String firstName
    String lastName
    Date signupDate
    Date lastLoginDate
    String status = "pending"
    String userType = "normal" // normal(by default) or project creator

    // Additional fields for project creators
    String identificationType
    String identificationNumber
    String locationId
    String address
    String zipCode
    String areaCode
    String phoneNumber
    String phoneExtension
    Boolean phoneVerified = false

    static constraints = {
        // Grails has nullable:false by default
        userId(unique:true, blank:false)
        email(unique:true, blank:false, maxSize:100, email:true)
        countryId(blank:false, maxSize:3)
        password(blank:false, size: 6..20)
        firstName(blank:false, maxSize:100)
        lastName(blank:false, maxSize:100)
        signupDate(blank:false)
        lastLoginDate(blank:false)
        status(blank:false, maxSize:20)
        userType(blank:false, maxSize:20)

        identificationType(nullable:true, blank:false, maxSize:5)
        identificationNumber(nullable:true, blank:false, maxSize:20)
        locationId(nullable: true, blank: false, maxSize: 40)
        address(nullable:true, blank:false, maxSize:100)
        zipCode(nullable:true, blank:false, maxSize:15)
        areaCode(nullable:true, blank:false, maxSize:10)
        phoneNumber(nullable:true, blank:false, maxSize:40)
        phoneExtension(nullable:true, blank:false, maxSize:20)
    }

    static mapping = {
        table 'user'
        version false
        id name: 'userId'
    }
}
