package edu.virginia.cs.androidapp3

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation


@Entity
data class Location(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)

// Credit to Gemini 3.1 Pro for showing how to add the foreign key constraint to the Tag entities
// It also showed me how to add the unique together with the index
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["location_id", "name"], unique = true)
    ]
)
data class Tag(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "location_id") val locationId: Int,
    val name: String
)

data class LocationWithTags(
    @Embedded val location: Location,
    @Relation(
        parentColumn = "id",
        entityColumn = "location_id"
    )
    val tags: List<Tag>
)