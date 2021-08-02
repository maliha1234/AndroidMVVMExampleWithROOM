package com.example.demoLogin.data.dao

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "person")
class PersonNew {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name: String
    var email: String
    var number: String? = null
    var pincode: String? = null
    var city: String? = null

    @Ignore
    constructor(name: String, email: String, number: String?, pincode: String?, city: String?) {
        this.name = name
        this.email = email
        this.number = number
        this.pincode = pincode
        this.city = city
    }

    @Ignore
    constructor(
        id: Int,
        name: String,
        email: String,
        number: String?,
        pincode: String?,
        city: String?
    ) {
        this.id = id
        this.name = name
        this.email = email
        this.number = number
        this.pincode = pincode
        this.city = city
    }

    constructor(name: String, email: String) {
        this.name = name
        this.email = email
    }
}