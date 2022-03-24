package com.adedom.myfood.di

import com.adedom.myfood.domain.usecase.auth.*
import com.adedom.myfood.domain.usecase.category.InsertCategoryUseCase
import com.adedom.myfood.domain.usecase.food.InsertFoodUseCase
import com.adedom.myfood.domain.usecase.food.MyFoodUseCase
import com.adedom.myfood.domain.usecase.profile.ChangeProfileUseCase
import com.adedom.myfood.domain.usecase.profile.DeleteAccountUseCase
import com.adedom.myfood.domain.usecase.profile.UserProfileUseCase
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
    bindSingleton { UserProfileUseCase(instance()) }
    bindSingleton { ChangeProfileUseCase(instance()) }
    bindSingleton { ChangePasswordUseCase(instance()) }
    bindSingleton { InsertCategoryUseCase(instance()) }
    bindSingleton { InsertFoodUseCase(instance()) }
}