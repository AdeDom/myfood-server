package com.adedom.myfood.data.repositories.category

import com.adedom.myfood.data.resouce.remote.category.CategoryRemoteDataSource
import com.adedom.myfood.route.models.request.InsertCategoryRequest

class CategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {

    override fun insertCategory(insertCategoryRequest: InsertCategoryRequest): Int? {
        return categoryRemoteDataSource.insertCategory(insertCategoryRequest)
    }
}