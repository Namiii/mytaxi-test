package gyg.demo.mytaxitest.data.model

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
data class Coordinate(
    @SerializedName("latitude")
    val lat: Float,
    @SerializedName("longitude")
    val long: Float
)