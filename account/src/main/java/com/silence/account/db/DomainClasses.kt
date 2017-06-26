package com.silence.account.db

/**
 * Created by wangpeng on 2017/6/26.
 */


data class AeraList(val id: Long, val name: String, val aeraList: List<Aera>) {

    val size: Int
        get() = aeraList.size

    operator fun get(position: Int) = aeraList[position]
}

data class Aera(val id: Long, val name: String, val cityId: String)