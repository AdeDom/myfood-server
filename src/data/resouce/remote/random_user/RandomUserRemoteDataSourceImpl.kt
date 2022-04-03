package com.adedom.myfood.data.resouce.remote.random_user

import com.adedom.myfood.data.resouce.data_source_provider.DataSourceProvider
import data.models.random_user.RandomUserResponse
import io.ktor.client.request.*

class RandomUserRemoteDataSourceImpl(
    private val dataSourceProvider: DataSourceProvider,
) : RandomUserRemoteDataSource {

    override suspend fun getRandomUser(): RandomUserResponse {
        return dataSourceProvider.getHttpClientOkHttp().get("https://randomuser.me/api/")
    }
}