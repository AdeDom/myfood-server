package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.route.models.response.MyFoodResponse

interface FoodRepository {

    fun myFood(): MyFoodResponse
}