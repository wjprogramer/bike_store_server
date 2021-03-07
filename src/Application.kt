package com.giant_giraffe

import com.giant_giraffe.app.JwtConfig
import com.giant_giraffe.app.initAuth
import com.giant_giraffe.app.initServices
import com.giant_giraffe.controllers.common.authController
import com.giant_giraffe.controllers.production.brandController
import com.giant_giraffe.controllers.production.categoryController
import com.giant_giraffe.controllers.production.productController
import com.giant_giraffe.controllers.production.stockController
import com.giant_giraffe.controllers.sales.*
import com.giant_giraffe.data.initDB
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.routing.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import java.text.DateFormat

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    val application = this
    di {
        bind<JwtConfig>() with singleton { JwtConfig(environment) }
        initServices(application)
    }

    initDB()
    initAuth()

    routing {

        // common
        authController()

        // production
        brandController()
        categoryController()
        productController()
        stockController()

        // sales
        customerController()
        orderController()
        orderItemController()
        staffController()
        storeController()

    }

}

