package com.adedom.myfood.di

import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val remoteDataSourceModule = DI.Module(name = "remote_data_source") {

    bindSingleton<MyFoodRemoteDataSource> { MyFoodRemoteDataSourceImpl() }
    bindSingleton<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
}