package gyg.demo.mytaxitest.data.model

sealed class TaxiType {
    object NormalTaxi : TaxiType()
    object PoolingTaxi : TaxiType()
}

val TaxiType.simpleName: String
    get() {
        return when (this) {

            TaxiType.NormalTaxi -> "Normal"
            TaxiType.PoolingTaxi -> "Pooling"
        }
    }
