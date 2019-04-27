package gyg.demo.mytaxitest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class TaxiType : Parcelable {
    POOLING,
    NORMAL
}
