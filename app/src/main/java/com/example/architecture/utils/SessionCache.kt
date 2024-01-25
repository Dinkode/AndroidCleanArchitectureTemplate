package com.example.architecture.utils

interface SessionCache {

    fun saveSession(session: Session)
    fun getSession(): Session?
    fun clearSession()

}