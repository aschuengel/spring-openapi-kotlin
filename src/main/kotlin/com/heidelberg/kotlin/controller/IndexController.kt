package com.heidelberg.kotlin.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class IndexController {
    @GetMapping("/")
    fun index() = "redirect:/swagger-ui.html"
}