package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.data.database.UserTable
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class AuthRemoteDataSourceImpl : AuthRemoteDataSource {

    override fun findUserByUsernameAndPassword(loginRequest: LoginRequest, status: String): UserEntity? {
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
                    (UserTable.username eq username!!) and (UserTable.password eq password!!) and (UserTable.status eq status)
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

    override fun findUserByUsername(username: String): Long {
        return transaction {
            UserTable
                .slice(
                    UserTable.username,
                )
                .select {
                    UserTable.username eq username
                }
                .count()
        }
    }

    override fun insertUser(userId: String, registerRequest: RegisterRequest, status: String): Int? {
        val (username, password, name) = registerRequest

        val statement = transaction {
            UserTable
                .insert {
                    it[UserTable.userId] = userId
                    it[UserTable.username] = username!!
                    it[UserTable.password] = password!!
                    it[UserTable.name] = name!!
                    it[UserTable.status] = status
                }
        }

        return statement.resultedValues?.size
    }

    override fun updateUserStatus(userId: String, status: String): Int {
        return transaction {
            UserTable.update({ UserTable.userId eq userId }) {
                it[UserTable.status] = status
            }
        }
    }
}