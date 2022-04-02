package com.adedom.myfood.di

import com.adedom.myfood.domain.usecase.auth.*
import com.adedom.myfood.domain.usecase.category.GetCategoryAllUseCase
import com.adedom.myfood.domain.usecase.category.InsertCategoryUseCase
import com.adedom.myfood.domain.usecase.favorite.DeleteFavoriteAllUseCase
import com.adedom.myfood.domain.usecase.favorite.GetFavoriteAllUseCase
import com.adedom.myfood.domain.usecase.favorite.MyFavoriteUseCase
import com.adedom.myfood.domain.usecase.favorite.SyncDataFavoriteUseCase
import com.adedom.myfood.domain.usecase.food.*
import com.adedom.myfood.domain.usecase.profile.ChangeProfileUseCase
import com.adedom.myfood.domain.usecase.profile.DeleteAccountUseCase
import com.adedom.myfood.domain.usecase.profile.UserProfileUseCase
import com.adedom.myfood.domain.usecase.rating_score.DeleteRatingScoreAllUseCase
import com.adedom.myfood.domain.usecase.rating_score.GetRatingScoreAllUseCase
import com.adedom.myfood.domain.usecase.rating_score.MyRatingScoreUseCase
import com.adedom.myfood.domain.usecase.rating_score.SyncDataRatingScoreUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val domainModule = DI.Module(name = "domain") {

    bindSingleton { MyFoodUseCase(instance()) }
    bindSingleton { LoginUseCase(instance()) }
    bindSingleton { RegisterUseCase(instance()) }
    bindSingleton { RefreshTokenUseCase(instance(), instance()) }
    bindSingleton { DeleteAccountUseCase(instance()) }
    bindSingleton { LogoutUseCase(instance()) }
    bindSingleton { UserProfileUseCase(instance()) }
    bindSingleton { ChangeProfileUseCase(instance()) }
    bindSingleton { ChangePasswordUseCase(instance()) }
    bindSingleton { InsertCategoryUseCase(instance()) }
    bindSingleton { InsertFoodUseCase(instance(), instance()) }
    bindSingleton { GetFoodDetailUseCase(instance()) }
    bindSingleton { GetCategoryAllUseCase(instance()) }
    bindSingleton { GetFoodByCategoryIdUseCase(instance()) }
    bindSingleton { GetFoodAndCategoryAllUseCase(instance()) }
    bindSingleton { GetFoodAndCategoryGroupAllUseCase(instance()) }
    bindSingleton { GetFavoriteAllUseCase(instance()) }
    bindSingleton { DeleteFavoriteAllUseCase(instance()) }
    bindSingleton { MyFavoriteUseCase(instance()) }
    bindSingleton { SyncDataFavoriteUseCase(instance()) }
    bindSingleton { GetRatingScoreAllUseCase(instance()) }
    bindSingleton { DeleteRatingScoreAllUseCase(instance()) }
    bindSingleton { MyRatingScoreUseCase(instance()) }
    bindSingleton { SyncDataRatingScoreUseCase(instance()) }
    bindSingleton { SyncDataAuthUseCase(instance()) }
}