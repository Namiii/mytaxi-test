package gyg.demo.mytaxitest.taxiList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gyg.demo.mytaxitest.any
import gyg.demo.mytaxitest.data.NoDataException
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.data.TaxiRepository
import gyg.demo.mytaxitest.data.model.*
import gyg.demo.mytaxitest.providers.ImmediateScheduleProviders
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.util.concurrent.TimeoutException

class TaxiListViewModelTest {

    @Rule
    @JvmField
    val taskRule = InstantTaskExecutorRule()

    private val repository = mock(TaxiRepository::class.java)

    private lateinit var viewModel: TaxiListViewModel

    private val taxiList = TaxiList(
        (1..30).map {
            Vehicle(
                it,
                Coordinate(
                    53.407547f,
                    9.8783910f
                ),
                TaxiType.NormalTaxi,
                310.30877f
            )
        }
    )

    @Before
    fun setup() {
        viewModel = TaxiListViewModel(
            repository,
            ImmediateScheduleProviders()
        )
    }

    @Test
    fun getInitTaxisReturnsData() {
        `when`(repository.getTaxis(any())).thenReturn(Observable.just(taxiList))

        viewModel.getInitTaxis()

        val testObserver = viewModel.data
        testObserver.run {
            assertEquals(ResultWrapper.Success::class.java, (value as ResultWrapper.Success)::class.java)
            assertEquals(taxiList.list.size, (value as ResultWrapper.Success).value.list.size)
        }

        verify(repository, times(1)).getTaxis(any())
    }

    @Test
    fun getPlaceTaxisReturnsData() {
        val place = Place(
            Coordinate(
                53.40754f,
                9.87839f
            ),
            Coordinate(
                53.61177f,
                9.99464f
            )
        )

        `when`(repository.getTaxis(any())).thenReturn(Observable.just(taxiList))

        viewModel.getTaxisAtPlace(
            place.bound1.lat,
            place.bound1.long,
            place.bound2.lat,
            place.bound2.long
        )

        val testObserver = viewModel.data
        testObserver.run {
            assertEquals(ResultWrapper.Success::class.java, (value as ResultWrapper.Success)::class.java)
            assertEquals(taxiList.list.size, (value as ResultWrapper.Success).value.list.size)
        }

        verify(repository, times(1)).getTaxis(any())
    }

    @Test
    fun checkGetTaxisReturnsNoData() {
        `when`(repository.getTaxis(any())).thenReturn(Observable.just(taxiList.copy(emptyList())))

        viewModel.getInitTaxis()

        val testObserver = viewModel.data

        testObserver.run {
            assertEquals(ResultWrapper.Failure::class.java, (value as ResultWrapper.Failure)::class.java)
            assertEquals(NoDataException().message, (value as ResultWrapper.Failure).error.message)

        }

        verify(repository, times(1)).getTaxis(any())
    }


    @Test
    fun checkGetTaxisReturnsApiError() {
        `when`(repository.getTaxis(any())).thenReturn(Observable.error(TimeoutException()))

        viewModel.getInitTaxis()

        val testObserver = viewModel.data

        testObserver.run {
            assertEquals(ResultWrapper.Failure::class.java, (value as ResultWrapper.Failure)::class.java)
            assertEquals(TimeoutException().message, (value as ResultWrapper.Failure).error.message)

        }

        verify(repository, times(1)).getTaxis(any())
    }
}
