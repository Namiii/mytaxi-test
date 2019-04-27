package gyg.demo.mytaxitest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaxiList (
    @SerializedName("poiList")
    val list: List<Vehicle>
) : Parcelable

fun TaxiList.hasData(): Boolean =
    !list.isNullOrEmpty()
