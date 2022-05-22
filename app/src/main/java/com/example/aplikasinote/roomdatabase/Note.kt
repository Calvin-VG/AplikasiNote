package com.example.aplikasinote.roomdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Note (

    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    @ColumnInfo(name = "judul")
    var judul : String,
    @ColumnInfo(name = "catatan")
    var catatan : String

) : Parcelable