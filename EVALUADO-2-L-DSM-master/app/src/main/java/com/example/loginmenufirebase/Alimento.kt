package com.example.loginmenufirebase

import android.os.Parcel
import android.os.Parcelable

data class Alimento(
    val nombre: String?,
    val precio: Double,
    var seleccionado: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeDouble(precio)
        parcel.writeByte(if (seleccionado) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alimento> {
        override fun createFromParcel(parcel: Parcel): Alimento {
            return Alimento(parcel)
        }

        override fun newArray(size: Int): Array<Alimento?> {
            return arrayOfNulls(size)
        }
    }
}
