package gyg.demo.mytaxitest.taxiMap.data

sealed class TaxiMapState {
    class SingleState(val id: Int) : TaxiMapState()
    object ListState : TaxiMapState()
}
