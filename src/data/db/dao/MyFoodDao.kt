package com.adedom.myfood.data.db.dao

import com.adedom.myfood.data.db.models.MyFoodEntity

interface MyFoodDao {

    fun getMyFood(): List<MyFoodEntity>
}