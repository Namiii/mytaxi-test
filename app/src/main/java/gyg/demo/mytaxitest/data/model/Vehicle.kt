package gyg.demo.mytaxitest.data.model

import com.google.gson.annotations.SerializedName

data class Vehicle(
    @SerializedName("id")
    val id: Int,
    @SerializedName("coordinate")
    val coordinate: Coordinate,
    @SerializedName("fleetType")
    val type: TaxiType,
    @SerializedName("heading")
    val headingAngle: Float
) {
    override fun toString(): String {
        return "id = $id," +
                " coordinate = Coordinate(lat = ${coordinate.lat}, long = ${coordinate.long})," +
                " type = ${type.javaClass.simpleName}," +
                " headingAngle = $headingAngle"
    }
}