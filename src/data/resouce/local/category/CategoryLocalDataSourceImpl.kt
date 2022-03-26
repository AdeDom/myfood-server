package com.adedom.myfood.data.resouce.local.category

import com.adedom.myfood.data.database.CategoryTable
import com.adedom.myfood.route.models.entities.CategoryEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryLocalDataSourceImpl(
    private val db: Database,
) : CategoryLocalDataSource {

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

    override fun insertCategoryAll(categoryList: List<CategoryEntity>): Int {
        val statement = transaction(db) {
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

    override fun deleteCategoryAll(): Int {
        return transaction(db) {
            CategoryTable.deleteAll()
        }
    }
}