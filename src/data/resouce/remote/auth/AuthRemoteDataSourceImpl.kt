package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.data.database.UserTable
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.utility.constant.AppConstant
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.joda.time.DateTime

class AuthRemoteDataSourceImpl(
    private val db: Database,
) : AuthRemoteDataSource {

    override suspend fun findUserIdByUsernameAndPassword(username: String, password: String, status: String): String? {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            UserTable
                .slice(
                    UserTable.userId,
                )
                .select {
                    (UserTable.username eq username) and (UserTable.password eq password) and (UserTable.status eq status)
                }
                .map { row ->
                    row[UserTable.userId]
                }
                .singleOrNull()
        }
    }

    override suspend fun findUserByUsername(username: String): Long {
        return newSuspendedTransaction(Dispatchers.IO, db) {
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

    override suspend fun insertUser(userId: String, registerRequest: RegisterRequest, status: String): Int? {
        val (username, password, name, email, mobileNo, address) = registerRequest

        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
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

    override suspend fun findUserByUserIdAndPassword(userId: String, password: String): Long {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            UserTable
                .select {
                    (UserTable.userId eq userId) and (UserTable.password eq password)
                }
                .count()
        }
    }

    override suspend fun updateUserPassword(userId: String, password: String): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            UserTable.update({ UserTable.userId eq userId }) {
                it[UserTable.password] = password
                it[updated] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
            }
        }
    }
}