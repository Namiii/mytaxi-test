package gyg.demo.mytaxitest.taxiList

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.TestSingleFragmentActivity
import gyg.demo.mytaxitest.data.NoDataException
import gyg.demo.mytaxitest.data.ResultWrapper
import gyg.demo.mytaxitest.data.model.*
import gyg.demo.mytaxitest.util.RecyclerViewItemCountAssertion
import gyg.demo.mytaxitest.util.nthChildOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@LargeTest
@RunWith(AndroidJUnit4::class)
class TaxiListFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(
        TestSingleFragmentActivity::class.java,
        true,
        true
    )

    private lateinit var fragment: TaxiListFragment
    private val viewModel = mock(TaxiListViewModel::class.java)

    private val data = MutableLiveData<ResultWrapper<TaxiList>>()
    private val taxiList = TaxiList(
        (1..NO_OF_TAXIS).map {
            Vehicle(
                it,
                Coordinate(
                    53.407547,
                    9.8783910
                ),
                TaxiType.NormalTaxi,
                310.30877f
            )
        }
    )
    private val mockError = Exception("This is an error, not good!")

    @Before
    fun setup() {
        fragment = TaxiListFragment()
        fragment.viewModel = viewModel

        `when`(viewModel.data).thenReturn(data)

        activityRule.activity.replaceFragment(fragment)
    }


    @Test
    fun getTaxisShowsDataOnList() {
        data.postValue(ResultWrapper.Success(taxiList))

        Espresso.onView(ViewMatchers.withId(R.id.taxi_list_recycler_view)).check(
            RecyclerViewItemCountAssertion(
                NO_OF_TAXIS
            )
        )
    }

    @Test
    fun geTaxisShowsNoData() {
        data.postValue(ResultWrapper.Success(taxiList.copy(emptyList())))

        onView(withId(R.id.taxi_list_recycler_view)).check(RecyclerViewItemCountAssertion(0))
        onView(withId(R.id.error_text_view)).check(matches(withText(R.string.list_error_message)))
    }


    @Test
    fun getTaxisShowsError() {
        data.postValue(ResultWrapper.Failure(mockError))

        onView(withId(R.id.taxi_list_recycler_view)).check(RecyclerViewItemCountAssertion(0))
        onView(withId(R.id.error_text_view)).check(matches(withText(R.string.list_error_message)))
    }

    @Test
    fun getTaxisCheckFirstItem() {
        data.postValue(ResultWrapper.Success(taxiList))

        val vehicle = taxiList.list.first()

        onView(nthChildOf(withId(R.id.taxi_list_item_id), 0)).check(matches(withText(vehicle.id.toString())))
        onView(nthChildOf(withId(R.id.taxi_list_item_type), 0)).check(matches(withText(vehicle.type.simpleName)))
    }

    @Test
    fun getTaxisWithDataCheckRecyclerViewVisible() {
        data.postValue(ResultWrapper.Success(taxiList))

        onView(withId(R.id.taxi_list_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun getTaxisNoDataCheckRecyclerViewNotVisible() {
        data.postValue(ResultWrapper.Failure(NoDataException()))

        onView(withId(R.id.taxi_list_recycler_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun getTaxisErrorCheckRecyclerViewNotVisible() {
        data.postValue(ResultWrapper.Failure(mockError))

        onView(withId(R.id.taxi_list_recycler_view)).check(matches(not(isDisplayed())))
    }

    companion object {
        private const val NO_OF_TAXIS = 30
    }
}