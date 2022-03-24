package com.adedom.myfood.di

import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.category.CategoryRemoteDataSource
import com.adedom.myfood.data.resouce.remote.category.CategoryRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSource
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val remoteDataSourceModule = DI.Module(name = "remote_data_source") {

    bindSingleton<MyFoodRemoteDataSource> { MyFoodRemoteDataSourceImpl() }
    bindSingleton<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
    bindSingleton<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
    bindSingleton<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl() }
    bindSingleton<FoodRemoteDataSource> { FoodRemoteDataSourceImpl() }
}