package com.simba.crud.mongo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Simba", email = "simba@gmail.com", url = "https://www.github.com/simba"),
                description = "Open API documentation for Mongo DB.",
                title = "Simba MongoDB demo",
                version = "1.0",
                license = @License(name = "Apache License", url = "https://www.apache.org/licenses/LICENSE-2"),
                termsOfService = "Terms of Service"
        )
)
public class OpenAPIConfiguration {
}
