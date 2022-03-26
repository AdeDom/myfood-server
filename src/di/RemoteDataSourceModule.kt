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
import com.adedom.myfood.utility.constant.AppConstant
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val remoteDataSourceModule = DI.Module(name = "remote_data_source") {

    bindSingleton<MyFoodRemoteDataSource> { MyFoodRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
}