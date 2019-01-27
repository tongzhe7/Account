package com.silence.account.db


import java.util.*

//数据类的构造函数
class City(val map: MutableMap<String, Any?>, val areas: List<Area>) {
    var _id: Long by map
    var text: String by map

    constructor(id: Long, text: String, areas: List<Area>)
            : this(HashMap(), areas) {
        this._id = id
        this.text = text
    }
}

class Area(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var text: String by map
    var cityId: Long by map

    constructor(id: Long, text: String,  cityId: Long)
            : this(HashMap()) {
        this.text = text
        this._id = id
        this.cityId = cityId
    }
}