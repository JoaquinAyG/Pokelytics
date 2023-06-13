package study.project.pokelytics.api.client

class ErrorResponse(val code: Int, message: String) : Throwable("($code) $message")
