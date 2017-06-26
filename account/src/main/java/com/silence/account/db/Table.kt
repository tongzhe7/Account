package com.silence.account.db

/**
 * Created by wangpeng on 2017/6/26.
 */
object CityTable{
    val TABLE_NAME = "City"
    val ID = "_id"
    val NAME ="name"
}

object AreaTable{
    val TABLE_NAME = "Area"
    val ID = "_id"
    val NAME ="name"
    val CITY_ID ="cityId"
}