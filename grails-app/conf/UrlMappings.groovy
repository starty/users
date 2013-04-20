class UrlMappings {

	static mappings = {
        "/$userId" (controller: "user") {
            action = [ GET: "getUserById", PUT: "updateUser"]
        }

        "/" (controller: "user") {
            action = [ POST: "createUser"]
        }
	}
}
