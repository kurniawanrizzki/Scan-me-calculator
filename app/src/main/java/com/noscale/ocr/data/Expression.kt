package com.noscale.ocr.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "ocr_expression")
data class Expression(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo("expression_name")
    var name: String? = null,
    @ColumnInfo("expression_result")
    var result: Int = 0
)
