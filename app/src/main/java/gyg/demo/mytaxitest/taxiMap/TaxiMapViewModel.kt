package gyg.demo.mytaxitest.taxiMap

import androidx.lifecycle.MutableLiveData
import gyg.demo.mytaxitest.core.BaseViewModel
import gyg.demo.mytaxitest.core.addTo
import gyg.demo.mytaxitest.taxiMap.data.NoCacheDataException
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.data.TaxiRepository
import gyg.demo.mytaxitest.data.model.*
import gyg.demo.mytaxitest.providers.BaseScheduleProvider
import gyg.demo.mytaxitest.taxiMap.data.NoMapDataException
import io.reactivex.Observable
import javax.inject.Inject

class TaxiMapViewModel @Inject constructor(
    private val repository: TaxiRepository,
    private val scheduleProvider: BaseScheduleProvider
) : BaseViewModel() {

    val listData = MutableLiveData<ResultWrapper<TaxiList>>()
    val singleData = MutableLiveData<ResultWrapper<Taxi>>()

    fun getTaxisAtBounds(
        bound1Lat: Double,
        bound1Lon: Double,
        bound2Lat: Double,
        bound2Lon: Double
    ) {
        Observable.just(
            Place(
                Coordinate(
                    bound1Lat,
                    bound1Lon
                ), Coordinate(
                    bound2Lat,
                    bound2Lon
                )
            )
        )
            .flatMap {
                repository.getTaxis(it, false)
            }
            .subscribeOn(scheduleProvider.io())
            .observeOn(scheduleProvider.ui())
            .subscribe({
                if (!it.hasData()) {
                    throw NoMapDataException()
                }

                listData.postValue(ResultWrapper.Success(it))
            }, {
                listData.postValue(ResultWrapper.Failure(it))
            })
            .addTo(disposable)
    }

    fun getSelectedTaxi(taxiId: Int) {
        Observable.just(taxiId)
            .map {
                repository.getSelectedTaxiFromCache(it)
            }
            .subscribeOn(scheduleProvider.io())
            .observeOn(scheduleProvider.ui())
            .subscribe({
                if (it == null) {
                    throw NoCacheDataException()
                }

                singleData.postValue(ResultWrapper.Success(it))
            }, {
                singleData.postValue(ResultWrapper.Failure(it))
            })
            .addTo(disposable)
    }
}
