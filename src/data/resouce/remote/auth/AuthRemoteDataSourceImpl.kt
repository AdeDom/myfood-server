package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.data.database.UserTable
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AuthRemoteDataSourceImpl : AuthRemoteDataSource {

    override fun callLogin(loginRequest: LoginRequest): UserEntity? {
        val (username, password) = loginRequest

        return transaction {
            UserTable
                .slice(
                    UserTable.userId,
                    UserTable.username,
                    UserTable.password,
                    UserTable.name,
                )
                .select {
                    (UserTable.username eq username!!) and (UserTable.password eq password!!)
                }
                .map { row ->
                    UserEntity(
                        userId = row[UserTable.userId],
                        username = row[UserTable.username],
                        password = row[UserTable.password],
                        name = row[UserTable.name],
                    )
                }
                .singleOrNull()
        }
    }
}