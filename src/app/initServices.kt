package com.giant_giraffe.app

import com.giant_giraffe.services.common.auth.AuthService
import com.giant_giraffe.services.common.auth.AuthServiceImpl
import com.giant_giraffe.services.common.user.UserService
import com.giant_giraffe.services.common.user.UserServiceImpl
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
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun DI.MainBuilder.initServices(application: Application) {

    // common
    bind<AuthService>() with singleton { AuthServiceImpl(application) }
    bind<UserService>() with singleton { UserServiceImpl(application) }

    // production
    bind<BrandService>() with singleton { BrandServiceImpl() }
    bind<CategoryService>() with singleton { CategoryServiceImpl() }
    bind<ProductService>() with singleton { ProductServiceImpl() }
    bind<StockService>() with singleton { StockServiceImpl() }

    // sales
    bind<CustomerService>() with singleton { CustomerServiceImpl() }
    bind<OrderService>() with singleton { OrderServiceImpl(application) }
    bind<OrderItemService>() with singleton { OrderItemServiceImpl() }
    bind<StaffService>() with singleton { StaffServiceImpl() }
    bind<StoreService>() with singleton { StoreServiceImpl() }

}