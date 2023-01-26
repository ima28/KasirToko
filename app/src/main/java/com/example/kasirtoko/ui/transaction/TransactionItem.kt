package com.example.kasirtoko.ui.transaction

import android.os.Parcel
import android.os.Parcelable
import com.example.kasirtoko.models.Product

data class TransactionItem (
    val product: Product,
    var amount: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("product"),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionItem> {
        override fun createFromParcel(parcel: Parcel): TransactionItem {
            return TransactionItem(parcel)
        }

        override fun newArray(size: Int): Array<TransactionItem?> {
            return arrayOfNulls(size)
        }
    }
}