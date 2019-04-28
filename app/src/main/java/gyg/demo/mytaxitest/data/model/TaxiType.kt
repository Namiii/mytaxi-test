package gyg.demo.mytaxitest.data.model

sealed class TaxiType {
    object NormalTaxi : TaxiType()
    object PoolingTaxi : TaxiType()
}

