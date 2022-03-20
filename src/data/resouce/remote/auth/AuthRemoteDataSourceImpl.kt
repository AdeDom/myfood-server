package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.data.database.UserTable
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.utility.constant.AppConstant
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

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
                    UserTable.email,
                    UserTable.mobileNo,
                    UserTable.address,
                    UserTable.status,
                    UserTable.created,
                    UserTable.updated,
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
                        email = row[UserTable.email],
                        mobileNo = row[UserTable.mobileNo],
                        address = row[UserTable.address],
                        status = row[UserTable.status],
                        created = row[UserTable.created],
                        updated = row[UserTable.updated],
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
        val (username, password, name, email, mobileNo, address) = registerRequest

        val statement = transaction {
            UserTable
                .insert {
                    it[UserTable.userId] = userId
                    it[UserTable.username] = username!!
                    it[UserTable.password] = password!!
                    it[UserTable.name] = name!!
                    it[UserTable.email] = email
                    it[UserTable.mobileNo] = mobileNo
                    it[UserTable.address] = address
                    it[UserTable.status] = status
                    it[created] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
                    it[updated] = null
                }
        }

        return statement.resultedValues?.size
    }
}