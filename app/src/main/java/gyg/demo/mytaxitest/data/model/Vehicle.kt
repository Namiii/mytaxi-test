package gyg.demo.mytaxitest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(
    @SerializedName("id")
    val id: Int,
    @SerializedName("coordinate")
    val coordinate: Coordinate,
    @SerializedName("fleetType")
    val type: TaxiType,
    @SerializedName("heading")
    val headingAngle: Float
) : Parcelable