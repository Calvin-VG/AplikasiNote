package com.example.aplikasinote.roomdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Akun(

    @PrimaryKey(autoGenerate = true)
    var id_akun : Int?,
    @ColumnInfo(name = "username")
    var username : String,
    @ColumnInfo(name = "email")
    var email : String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "konfirmasi_password")
    var konfirmasi_password: String

) : Parcelable