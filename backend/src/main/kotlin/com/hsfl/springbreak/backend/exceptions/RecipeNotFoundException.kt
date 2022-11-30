package com.hsfl.springbreak.backend.exceptions

import org.springframework.http.HttpStatus

class RecipeNotFoundException(notFound: HttpStatus, s: String) {

}
