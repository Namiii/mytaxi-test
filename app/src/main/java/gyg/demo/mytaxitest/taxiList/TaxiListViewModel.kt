package gyg.demo.mytaxitest.taxiList

import androidx.lifecycle.MutableLiveData
import gyg.demo.mytaxitest.core.BaseViewModel
import gyg.demo.mytaxitest.core.addTo
import gyg.demo.mytaxitest.data.NoDataException
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.data.TaxiRepository
import gyg.demo.mytaxitest.data.model.Coordinate
import gyg.demo.mytaxitest.data.model.Place
import gyg.demo.mytaxitest.data.model.TaxiList
import gyg.demo.mytaxitest.data.model.hasData
import gyg.demo.mytaxitest.providers.BaseScheduleProvider
import gyg.demo.mytaxitest.taxiList.data.Hamburg
import io.reactivex.Observable
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class TaxiListViewModel @Inject constructor(
    private val repository: TaxiRepository,
    private val scheduleProvider: BaseScheduleProvider
) : BaseViewModel() {

    val data = MutableLiveData<ResultWrapper<TaxiList>>()

    fun getInitTaxis() {
        Observable.just(Hamburg())
            .flatMap {
                repository.getTaxis(it)
            }
            .subscribeOn(scheduleProvider.io())
            .observeOn(scheduleProvider.ui())
            .subscribe({
                if (!it.hasData()) {
                    throw NoDataException()
                }

                data.postValue(ResultWrapper.Success(it))
            }, {
                data.postValue(ResultWrapper.Failure(it))
            })
            .addTo(disposable)
    }

    @TestOnly
    fun getTaxisAtPlace(
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
                repository.getTaxis(it)
            }
            .subscribeOn(scheduleProvider.io())
            .observeOn(scheduleProvider.ui())
            .subscribe({
                if (!it.hasData()) {
                    throw NoDataException()
                }

                data.postValue(ResultWrapper.Success(it))
            }, {
                data.postValue(ResultWrapper.Failure(it))
            })
            .addTo(disposable)
    }
}
