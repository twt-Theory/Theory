package com.example.twttheory.preference

import com.orhanobut.hawk.Hawk
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> hawk(key: String, default: T) = object : ReadWriteProperty<Any?, T> {
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        Hawk.get(key, default)

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        Hawk.put(key, value)
    }
}

//fun shared(key: String, default: Boolean) = object : ReadWriteProperty<Any?, Boolean> {
//    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
//        CommonContext.defaultSharedPreferences.getBoolean(key, default)
//
//    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
//        CommonContext.defaultSharedPreferences.edit().putBoolean(key, value).apply()
//    }
//}