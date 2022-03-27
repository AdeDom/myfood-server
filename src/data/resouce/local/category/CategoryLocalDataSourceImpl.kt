package com.adedom.myfood.data.resouce.local.category

import com.adedom.myfood.data.database.remote.CategoryTable
import com.adedom.myfood.route.models.entities.CategoryEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CategoryLocalDataSourceImpl(
    private val db: Database,
) : CategoryLocalDataSource {

    override suspend fun getCategoryAll(): List<CategoryEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
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

    override suspend fun insertCategoryAll(categoryList: List<CategoryEntity>): Int {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            CategoryTable.batchInsert(categoryList) { categoryEntity ->
                this[CategoryTable.categoryId] = categoryEntity.categoryId
                this[CategoryTable.categoryName] = categoryEntity.categoryName
                this[CategoryTable.image] = categoryEntity.image
                this[CategoryTable.created] = categoryEntity.created
                this[CategoryTable.updated] = categoryEntity.updated
            }
        }

        return statement.size
    }

    override suspend fun deleteCategoryAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            CategoryTable.deleteAll()
        }
    }
}