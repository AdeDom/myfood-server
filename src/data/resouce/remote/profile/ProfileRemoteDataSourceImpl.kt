package com.adedom.myfood.data.resouce.remote.profile

import com.adedom.myfood.data.database.UserTable
import com.adedom.myfood.utility.constant.AppConstant
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime

class ProfileRemoteDataSourceImpl : ProfileRemoteDataSource {

    override fun updateUserStatus(userId: String, status: String): Int {
        return transaction {
            UserTable.update({ UserTable.userId eq userId }) {
                it[UserTable.status] = status
                it[updated] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
            }
        }
    }
}