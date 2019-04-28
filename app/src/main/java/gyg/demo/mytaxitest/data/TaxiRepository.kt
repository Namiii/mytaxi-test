package gyg.demo.mytaxitest.data

import gyg.demo.mytaxitest.data.model.Place
import gyg.demo.mytaxitest.data.model.TaxiList
import gyg.demo.mytaxitest.data.model.Vehicle
import gyg.demo.mytaxitest.ui.list.Hamburg
import io.reactivex.Observable
import javax.inject.Inject

class TaxiRepository @Inject constructor(
    private val taxiListService: TaxiListService
) {

    private var taxiListCache = ArrayList<Vehicle>()

    fun getTaxis(place: Place): Observable<TaxiList> {
        place.apply {
            return taxiListService.getTaxis(
                bound1.lat,
                bound1.long,
                bound2.lat,
                bound2.long
            )
                .doOnNext {
                    reloadCache(it.list)
                }
        }
    }

    fun getInitTaxis(): Observable<TaxiList> = getTaxis(Hamburg())

    fun hasCachedTaxiList() = taxiListCache.size > 0

    fun getCachedTaxiList() = taxiListCache

    private fun reloadCache(list: List<Vehicle>) {
        taxiListCache.clear()
        taxiListCache.addAll(list)
    }
}
