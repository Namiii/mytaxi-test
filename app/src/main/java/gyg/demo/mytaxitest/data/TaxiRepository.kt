package gyg.demo.mytaxitest.data

import gyg.demo.mytaxitest.OpenClassOnDebug
import gyg.demo.mytaxitest.data.model.Place
import gyg.demo.mytaxitest.data.model.Taxi
import gyg.demo.mytaxitest.data.model.TaxiList
import io.reactivex.Observable
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenClassOnDebug
class TaxiRepository @Inject constructor(
    private val taxiListService: TaxiService
) {

    private var taxiListCache = TaxiList(ArrayList())

    fun getTaxis(place: Place, cacheResults: Boolean = true): Observable<TaxiList> {
        place.apply {
            return taxiListService.getTaxis(
                bound1.lat,
                bound1.long,
                bound2.lat,
                bound2.long
            )
                .doOnNext {
                    if (cacheResults) {
                        reloadCache(it)
                    }
                }
        }
    }

    fun hasCachedTaxiList() = taxiListCache.list.isNotEmpty()

    @TestOnly
    fun getCachedTaxiList() = taxiListCache

    fun getSelectedTaxiFromCache(taxiId: Int): Taxi? {
        if (!hasCachedTaxiList()) return null

        return taxiListCache.list.find {
            it.id == taxiId
        }
    }

    private fun reloadCache(taxiList: TaxiList) {
        taxiListCache = taxiList
    }
}
