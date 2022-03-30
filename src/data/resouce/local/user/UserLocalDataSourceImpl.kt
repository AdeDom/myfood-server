package com.adedom.myfood.data.resouce.local.user

import com.adedom.myfood.data.database.mysql.UserTable
import com.adedom.myfood.route.models.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserLocalDataSourceImpl(
    private val db: Database,
) : UserLocalDataSource {

    override suspend fun getUserByUserId(userId: String): UserEntity? {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            UserTable
                .slice(
                    UserTable.userId,
                    UserTable.username,
                    UserTable.name,
                    UserTable.email,
                    UserTable.mobileNo,
                    UserTable.address,
                    UserTable.status,
                    UserTable.created,
                    UserTable.updated,
                )
                .select {
                    UserTable.userId eq userId
                }
                .map { row ->
                    UserEntity(
                        userId = row[UserTable.userId],
                        username = row[UserTable.username],
                        password = "",
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

    override suspend fun getUserAll(): List<UserEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            UserTable
                .slice(
                    UserTable.userId,
                    UserTable.username,
                    UserTable.name,
                    UserTable.email,
                    UserTable.mobileNo,
                    UserTable.address,
                    UserTable.status,
                    UserTable.created,
                    UserTable.updated,
                )
                .selectAll()
                .map { row ->
                    UserEntity(
                        userId = row[UserTable.userId],
                        username = row[UserTable.username],
                        password = "",
                        name = row[UserTable.name],
                        email = row[UserTable.email],
                        mobileNo = row[UserTable.mobileNo],
                        address = row[UserTable.address],
                        status = row[UserTable.status],
                        created = row[UserTable.created],
                        updated = row[UserTable.updated],
                    )
                }
        }
    }

    override suspend fun insertUserAll(userList: List<UserEntity>): Int {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            UserTable.batchInsert(userList) { userEntity ->
                this[UserTable.userId] = userEntity.userId
                this[UserTable.username] = userEntity.username
                this[UserTable.password] = ""
                this[UserTable.name] = userEntity.name
                this[UserTable.email] = userEntity.email
                this[UserTable.mobileNo] = userEntity.mobileNo
                this[UserTable.address] = userEntity.address
                this[UserTable.status] = userEntity.status
                this[UserTable.created] = userEntity.created
                this[UserTable.updated] = userEntity.updated
            }
        }

        return statement.size
    }

    override suspend fun deleteUserAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            UserTable.deleteAll()
        }
    }
}