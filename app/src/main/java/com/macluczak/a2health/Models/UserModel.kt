package com.macluczak.a2health.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
    val username: String? = null,
    val password: String? = null
) : Parcelable