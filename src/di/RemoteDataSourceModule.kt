package com.adedom.myfood.di

import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val remoteDataSourceModule = DI.Module(name = "remote_data_source") {

    bindSingleton<MyFoodRemoteDataSource> { MyFoodRemoteDataSourceImpl(instance()) }
}