package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.cart

class CartItemSchema(val mId: String,
                     val mTitle: String,
                     val mDescription: String,
                     val mPrice: Int)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartItemSchema

        if (mId != other.mId) return false
        if (mTitle != other.mTitle) return false
        if (mDescription != other.mDescription) return false
        if (mPrice != other.mPrice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mId.hashCode()
        result = 31 * result + mTitle.hashCode()
        result = 31 * result + mDescription.hashCode()
        result = 31 * result + mPrice
        return result
    }
}