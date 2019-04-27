package gyg.demo.mytaxitest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(
    @SerializedName("latitude")
    val lat: Float,
    @SerializedName("longitude")
    val long: Float
) : Parcelable