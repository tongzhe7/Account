package com.silence.account.db

/**
 * Created by wangpeng on 2017/6/26.
 */

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.silence.account.application.AccountApplication

//import org.jetbrains.anko.db.*
import org.jetbrains.anko.db.*

class CityDbHelper(ctx: Context = AccountApplication.getApplication()) : ManagedSQLiteOpenHelper(ctx,
        CityDbHelper.DB_NAME, null, CityDbHelper.DB_VERSION) {

    companion object {
        val DB_NAME = "City.db"
        val DB_VERSION = 1
        val instance by lazy { CityDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(CityTable.TABLE_NAME, true,
                CityTable.ID to INTEGER + PRIMARY_KEY,
                CityTable.NAME to TEXT)

        db.createTable(AreaTable.TABLE_NAME, true,
                AreaTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                AreaTable.NAME to TEXT,
                AreaTable.CITY_ID to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(CityTable.NAME, true)
        db.dropTable(AreaTable.NAME, true)
        onCreate(db)
    }
}

