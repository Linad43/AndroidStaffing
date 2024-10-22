package com.example.androidstaffing.model

import android.os.Parcel
import android.os.Parcelable

class Person(
    val secondName: String?,
    val firstName: String?,
    val age: Int,
    val role: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "$secondName $firstName ($age) должность: $role"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(secondName)
        parcel.writeString(firstName)
        parcel.writeInt(age)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}