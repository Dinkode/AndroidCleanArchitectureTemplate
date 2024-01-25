package com.example.architecture.utils

import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class SessionCacheImpl(private val sharedPreferences: SharedPreferences) : SessionCache {

    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<Session> = moshi.adapter(Session::class.java)

    override fun saveSession(session: Session) {
        with (sharedPreferences.edit()) {
            putString("session", jsonAdapter.toJson(session))
            apply()
        }
    }

    override fun getSession(): Session? {
        return sharedPreferences.getString("session", "")?.let { jsonAdapter.fromJson(it) }
    }

    override fun clearSession() {
        TODO("Not yet implemented")
    }

}