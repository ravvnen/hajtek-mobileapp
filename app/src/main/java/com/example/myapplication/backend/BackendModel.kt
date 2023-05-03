package com.example.myapplication.backend

import com.example.myapplication.R



data class Boss(
    val name: String,
    val level: String,
    val maxHit: String,
    val attackStyle: String,
    val imageName: Int,
)

data class ImageData(val imageName: String)

val imageDataList = listOf(
   R.drawable.maiden,
    R.drawable.pestilentbloat,
    R.drawable.nylocasvasilias,
    R.drawable.sotetseg
)


val Amascut_Bosses =
    listOf(
        Boss("Maiden","940","36","Magic", R.drawable.maiden),
        Boss("Pestilent Bloat","870","80","Ranged", R.drawable.pestilentbloat),
        Boss("Nylocas Vasilias", "800","70","Stab, Magic, Ranged", R.drawable.nylocasvasilias),
        Boss("Sotetseg","995","45","Crush, Magic", R.drawable.sotetseg)
        )
