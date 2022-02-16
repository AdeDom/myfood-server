package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.db.models.MyFoodEntity
import com.adedom.myfood.route.models.response.base.BaseResponse

interface FoodRepository {

    fun getMyFood(): BaseResponse<List<MyFoodEntity>>
}