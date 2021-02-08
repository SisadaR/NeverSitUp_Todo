package com.sisada.neversituptodo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val __v: Int,
    val _id: String,
    var completed: Boolean,
    val createdAt: String,
    var description: String,
    val owner: String,
    val updatedAt: String,
) : Parcelable