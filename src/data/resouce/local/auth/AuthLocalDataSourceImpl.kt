package com.adedom.myfood.data.resouce.local.auth

import com.adedom.myfood.data.database.sqlite.AuthTableSqlite
import com.adedom.myfood.data.models.entities.AuthEntity
import com.adedom.myfood.utility.constant.AppConstant
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.joda.time.DateTime

class AuthLocalDataSourceImpl(
    private val db: Database,
) : AuthLocalDataSource {

    override suspend fun insertAuth(
        authId: String,
        accessToken: String,
        refreshToken: String,
        status: String,
        isBackup: Int,
    ): Int? {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            AuthTableSqlite.insert {
                it[AuthTableSqlite.authId] = authId
                it[AuthTableSqlite.accessToken] = accessToken
                it[AuthTableSqlite.refreshToken] = refreshToken
                it[AuthTableSqlite.status] = status
                it[AuthTableSqlite.isBackup] = isBackup
                val currentDateTime = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
                val currentDateTimeString = currentDateTime.toString(AppConstant.DATE_TIME_FORMAT_REQUEST)
                it[created] = currentDateTimeString
                it[updated] = null
            }
        }

        return statement.resultedValues?.size
    }

    override suspend fun getAuthListByStatusLoginOrRefresh(): List<AuthEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            AuthTableSqlite
                .slice(
                    AuthTableSqlite.authId,
                    AuthTableSqlite.accessToken,
                    AuthTableSqlite.refreshToken,
                    AuthTableSqlite.status,
                    AuthTableSqlite.isBackup,
                    AuthTableSqlite.created,
                    AuthTableSqlite.updated,
                )
                .select {
                    (AuthTableSqlite.status eq AppConstant.AUTH_LOGIN) or (AuthTableSqlite.status eq AppConstant.AUTH_REFRESH)
                }
                .map { row ->
                    AuthEntity(
                        authId = row[AuthTableSqlite.authId],
                        accessToken = row[AuthTableSqlite.accessToken],
                        refreshToken = row[AuthTableSqlite.refreshToken],
                        status = row[AuthTableSqlite.status],
                        isBackup = row[AuthTableSqlite.isBackup],
                        created = row[AuthTableSqlite.created],
                        updated = row[AuthTableSqlite.updated],
                    )
                }
        }
    }

    override suspend fun updateAuthStatusLogoutByAuthId(authId: String): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            AuthTableSqlite.update({ AuthTableSqlite.authId eq authId }) {
                it[status] = AppConstant.AUTH_LOGOUT
                val currentDateTime = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
                val currentDateTimeString = currentDateTime.toString(AppConstant.DATE_TIME_FORMAT_REQUEST)
                it[updated] = currentDateTimeString
            }
        }
    }
}