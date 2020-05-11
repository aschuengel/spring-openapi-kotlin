package com.heidelberg.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
class KotlinApplication {
    @Bean
    fun api(): Docket? = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(KotlinApplication::class.java.packageName))
            .paths(PathSelectors.any())
            .build()
}

fun main(args: Array<String>) {
    runApplication<KotlinApplication>(*args)
}
