package com.example.wwmweaponscompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "weapons")
@Serializable
data class Weapon(
    @PrimaryKey
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("description") val description: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("info_url") val infoUrl: String
)