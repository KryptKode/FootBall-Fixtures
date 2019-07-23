package com.kryptkode.footballfixtures.app.data.models.competition

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Competition(
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("name") val name: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
    }

    companion object {
        val diffUtilItemCallback = object : DiffUtil.ItemCallback<Competition>() {
            override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
                return newItem == oldItem
            }

        }

        @JvmField
        val CREATOR: Parcelable.Creator<Competition> = object : Parcelable.Creator<Competition> {
            override fun createFromParcel(source: Parcel): Competition = Competition(source)
            override fun newArray(size: Int): Array<Competition?> = arrayOfNulls(size)
        }
    }
}