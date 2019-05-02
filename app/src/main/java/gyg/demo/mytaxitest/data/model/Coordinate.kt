package gyg.demo.mytaxitest.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("latitude")
    val lat: Double,
    @SerializedName("longitude")
    val long: Double
)

fun Coordinate.toLatLng(): LatLng {
    return LatLng(lat,long)
}