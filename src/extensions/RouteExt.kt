package com.giant_giraffe.extensions

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

fun Route.authPost(
    path: String,
    body: PipelineInterceptor<Unit, ApplicationCall>
): Route {
    return authenticate {
        route(path, HttpMethod.Post) { handle(body) }
    }
}

fun Route.authPost(
    body: PipelineInterceptor<Unit, ApplicationCall>
): Route {
    return authenticate {
        method(HttpMethod.Post) { handle(body) }
    }
}

fun Route.authGet(body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return authenticate {
        method(HttpMethod.Get) { handle(body) }
    }
}

fun Route.authGet(path: String, body: PipelineInterceptor<Unit, ApplicationCall>): Route {
    return authenticate {
        route(path, HttpMethod.Get) { handle(body) }
    }
}
