package gyg.demo.mytaxitest.data.model

val TaxiType.simpleName: String
    get() {
        return when (this) {

            TaxiType.NormalTaxi -> "Normal"
            TaxiType.PoolingTaxi -> "Pooling"
        }
    }
