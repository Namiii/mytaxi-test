package gyg.demo.mytaxitest.data.model

import org.parceler.Parcel

@Parcel
sealed class TaxiType {
    object poolingTaxi : TaxiType()
    object normalTaxi : TaxiType()
}