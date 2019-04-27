package gyg.demo.mytaxitest.data.model

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
data class TaxiList(
    @SerializedName("poiList")
    val list: List<Vehicle>
)

fun TaxiList.hasData(): Boolean =
    !list.isNullOrEmpty()
