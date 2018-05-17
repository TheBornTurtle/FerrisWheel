package me.turtlecode.ferriswheel.model

import android.graphics.drawable.GradientDrawable

data class User (val name: String,
                 val chats: Int,
                 val gender: Int,
                 val orientation: Int,
                 val relationship: Int,
                 val phoneNumber: String) {
    constructor(): this("", 0, 0, 0, 0,"")
}