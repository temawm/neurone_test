package com.example.test_neurone.core.string_provider

import android.content.Context

interface StringProvider {
    fun getString(key: String): String
}

class StringProviderImpl(private val context: Context) : StringProvider {
    override fun getString(key: String): String {
        val resId = context.resources.getIdentifier(key, "string", context.packageName)
        return if (resId != 0) context.getString(resId) else "String not found: $key"
    }
}