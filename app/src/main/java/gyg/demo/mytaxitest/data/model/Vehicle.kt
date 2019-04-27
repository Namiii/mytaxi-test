package gyg.demo.mytaxitest.data.model

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
data class Vehicle(
    @SerializedName("id")
    val id: Int,
    @SerializedName("coordinate")
    val coordinate: Coordinate,
    @SerializedName("fleetType")
    val type: TaxiType,
    @SerializedName("heading")
    val headingAngle: Float
)