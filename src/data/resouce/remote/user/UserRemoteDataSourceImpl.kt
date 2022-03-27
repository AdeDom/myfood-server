package com.adedom.myfood.data.resouce.remote.user

import com.adedom.myfood.data.database.UserTable
import com.adedom.myfood.route.models.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRemoteDataSourceImpl(
    private val db: Database,
) : UserRemoteDataSource {

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
}