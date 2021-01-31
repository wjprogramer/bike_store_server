package com.giant_giraffe

import com.giant_giraffe.controllers.production.brandController
import com.giant_giraffe.controllers.production.categoryController
import com.giant_giraffe.controllers.production.productController
import com.giant_giraffe.controllers.production.stockController
import com.giant_giraffe.controllers.sales.*
import com.giant_giraffe.data.initDB
import com.giant_giraffe.services.production.brand.BrandService
import com.giant_giraffe.services.production.brand.BrandServiceImpl
import com.giant_giraffe.services.production.category.CategoryService
import com.giant_giraffe.services.production.category.CategoryServiceImpl
import com.giant_giraffe.services.production.product.ProductService
import com.giant_giraffe.services.production.product.ProductServiceImpl
import com.giant_giraffe.services.production.stock.StockService
import com.giant_giraffe.services.production.stock.StockServiceImpl
import com.giant_giraffe.services.sales.customer.CustomerService
import com.giant_giraffe.services.sales.customer.CustomerServiceImpl
import com.giant_giraffe.services.sales.order.OrderService
import com.giant_giraffe.services.sales.order.OrderServiceImpl
import com.giant_giraffe.services.sales.order_item.OrderItemService
import com.giant_giraffe.services.sales.order_item.OrderItemServiceImpl
import com.giant_giraffe.services.sales.staff.StaffService
import com.giant_giraffe.services.sales.staff.StaffServiceImpl
import com.giant_giraffe.services.sales.store.StoreService
import com.giant_giraffe.services.sales.store.StoreServiceImpl
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

    initDB()

    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    di {
        // ## services
        // production
        bind<BrandService>() with singleton { BrandServiceImpl() }
        bind<CategoryService>() with singleton { CategoryServiceImpl() }
        bind<ProductService>() with singleton { ProductServiceImpl() }
        bind<StockService>() with singleton { StockServiceImpl() }
        // sales
        bind<CustomerService>() with singleton { CustomerServiceImpl() }
        bind<OrderService>() with singleton { OrderServiceImpl() }
        bind<OrderItemService>() with singleton { OrderItemServiceImpl() }
        bind<StaffService>() with singleton { StaffServiceImpl() }
        bind<StoreService>() with singleton { StoreServiceImpl() }
    }

    routing {

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

