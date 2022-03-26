package com.adedom.myfood.data.resouce.remote.category

import com.adedom.myfood.data.database.CategoryTable
import com.adedom.myfood.route.models.entities.CategoryEntity
import com.adedom.myfood.route.models.request.InsertCategoryRequest
import com.adedom.myfood.utility.constant.AppConstant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class CategoryRemoteDataSourceImpl(
    private val db: Database,
) : CategoryRemoteDataSource {

    override fun findCategoryId(categoryId: Int): Long {
        return transaction(db) {
            CategoryTable
                .select {
                    CategoryTable.categoryId eq categoryId
                }
                .count()
        }
    }

    override fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int? {
        val (categoryName, image) = insertCategoryRequest

        val statement = transaction(db) {
            CategoryTable.insert {
                it[CategoryTable.categoryName] = categoryName!!
                it[CategoryTable.image] = image!!
                it[created] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
            }
        }

        return statement.resultedValues?.size
    }

    override fun getCategoryAll(): List<CategoryEntity> {
        return transaction(db) {
            CategoryTable
                .slice(
                    CategoryTable.categoryId,
                    CategoryTable.categoryName,
                    CategoryTable.image,
                    CategoryTable.created,
                    CategoryTable.updated,
                )
                .selectAll()
                .map { row ->
                    CategoryEntity(
                        categoryId = row[CategoryTable.categoryId],
                        categoryName = row[CategoryTable.categoryName],
                        image = row[CategoryTable.image],
                        created = row[CategoryTable.created],
                        updated = row[CategoryTable.updated],
                    )
                }
        }
    }
}