package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.route.models.response.MyFoodResponse

class FoodRepositoryImpl : FoodRepository {

    override fun myFood(): MyFoodResponse {
        return MyFoodResponse("Hello, ktor my food.")
    }
}