package com.adedom.myfood.data.resouce.remote.profile

import com.adedom.myfood.data.database.UserTable
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.ChangeProfileRequest
import com.adedom.myfood.utility.constant.AppConstant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime

class ProfileRemoteDataSourceImpl(
    private val db: Database,
) : ProfileRemoteDataSource {

    override fun getUserByUserId(userId: String): UserEntity? {
        return transaction(db) {
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
                    UserTable.userId eq userId
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

    override fun updateUserProfile(userId: String, changeProfileRequest: ChangeProfileRequest): Int {
        val (name, email, mobileNo, address) = changeProfileRequest

        return transaction(db) {
            UserTable.update({ UserTable.userId eq userId }) {
                it[UserTable.name] = name!!
                it[UserTable.email] = email
                it[UserTable.mobileNo] = mobileNo
                it[UserTable.address] = address
                it[updated] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
            }
        }
    }

    override fun updateUserStatus(userId: String, status: String): Int {
        return transaction(db) {
            UserTable.update({ UserTable.userId eq userId }) {
                it[UserTable.status] = status
                it[updated] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
            }
        }
    }
}