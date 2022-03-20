package com.adedom.myfood.di

import com.adedom.myfood.domain.usecase.auth.LoginUseCase
import com.adedom.myfood.domain.usecase.auth.LogoutUseCase
import com.adedom.myfood.domain.usecase.auth.RefreshTokenUseCase
import com.adedom.myfood.domain.usecase.auth.RegisterUseCase
import com.adedom.myfood.domain.usecase.food.MyFoodUseCase
import com.adedom.myfood.domain.usecase.profile.DeleteAccountUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val domainModule = DI.Module(name = "domain") {

    bindSingleton { MyFoodUseCase(instance()) }
    bindSingleton { LoginUseCase(instance(), instance()) }
    bindSingleton { RegisterUseCase(instance(), instance()) }
    bindSingleton { RefreshTokenUseCase(instance()) }
    bindSingleton { DeleteAccountUseCase(instance()) }
    bindSingleton { LogoutUseCase() }
}