package com.adedom.myfood.data.resouce.local.user

import com.adedom.myfood.data.database.mysql.UserTable
import com.adedom.myfood.data.models.entities.UserEntity
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
                    UserTable.email,
                    UserTable.name,
                    UserTable.mobileNo,
                    UserTable.address,
                    UserTable.image,
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
                        email = row[UserTable.email],
                        password = "",
                        name = row[UserTable.name],
                        mobileNo = row[UserTable.mobileNo],
                        address = row[UserTable.address],
                        image = row[UserTable.image],
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
                    UserTable.email,
                    UserTable.name,
                    UserTable.mobileNo,
                    UserTable.address,
                    UserTable.image,
                    UserTable.status,
                    UserTable.created,
                    UserTable.updated,
                )
                .selectAll()
                .map { row ->
                    UserEntity(
                        userId = row[UserTable.userId],
                        email = row[UserTable.email],
                        password = "",
                        name = row[UserTable.name],
                        mobileNo = row[UserTable.mobileNo],
                        address = row[UserTable.address],
                        image = row[UserTable.image],
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
                this[UserTable.email] = userEntity.email
                this[UserTable.password] = ""
                this[UserTable.name] = userEntity.name
                this[UserTable.mobileNo] = userEntity.mobileNo
                this[UserTable.address] = userEntity.address
                this[UserTable.image] = userEntity.image
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