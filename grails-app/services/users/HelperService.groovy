package users

import grails.converters.JSON

class HelperService {

    def httpStatusCodes = ["400":"bad_request",
            "404":"not_found",
            "403":"forbidden",
            "500":"internal_server_error"]

    JSON renderError(String errorMessage, String statusCode) {
        def error = [:]
        error.put("message", errorMessage)
        error.put("status_code", statusCode)
        error.put("error_type", httpStatusCodes[statusCode])
        return error as JSON
    }
}
