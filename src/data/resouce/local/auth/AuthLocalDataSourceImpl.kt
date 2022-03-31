package com.adedom.myfood.data.resouce.local.auth

import com.adedom.myfood.data.database.sqlite.AuthTableSqlite
import com.adedom.myfood.data.models.entities.AuthEntity
import com.adedom.myfood.utility.constant.AppConstant
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.joda.time.DateTime

class AuthLocalDataSourceImpl(
    private val db: Database,
) : AuthLocalDataSource {

    override suspend fun replaceAuth(authId: String, accessToken: String, refreshToken: String, isBackup: Int): Int? {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            AuthTableSqlite.replace {
                it[AuthTableSqlite.authId] = authId
                it[AuthTableSqlite.accessToken] = accessToken
                it[AuthTableSqlite.refreshToken] = refreshToken
                it[AuthTableSqlite.isBackup] = isBackup
                val currentDateTime = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
                val currentDateTimeString = currentDateTime.toString(AppConstant.DATE_TIME_FORMAT_REQUEST)
                it[created] = currentDateTimeString
                it[updated] = null
            }
        }

        return statement.resultedValues?.size
    }

    override suspend fun getAuthList(): List<AuthEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            AuthTableSqlite
                .slice(
                    AuthTableSqlite.authId,
                    AuthTableSqlite.accessToken,
                    AuthTableSqlite.refreshToken,
                    AuthTableSqlite.isBackup,
                    AuthTableSqlite.created,
                    AuthTableSqlite.updated,
                )
                .selectAll()
                .map { row ->
                    AuthEntity(
                        authId = row[AuthTableSqlite.authId],
                        accessToken = row[AuthTableSqlite.accessToken],
                        refreshToken = row[AuthTableSqlite.refreshToken],
                        isBackup = row[AuthTableSqlite.isBackup],
                        created = row[AuthTableSqlite.created],
                        updated = row[AuthTableSqlite.updated],
                    )
                }
        }
    }
}