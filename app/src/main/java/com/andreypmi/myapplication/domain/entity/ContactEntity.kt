package com.andreypmi.myapplication.domain.entity

import androidx.fragment.app.Fragment

data class ContactEntity(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String
)
