package gyg.demo.mytaxitest.taxiMap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.data.model.Place
import gyg.demo.mytaxitest.data.model.TaxiType
import gyg.demo.mytaxitest.data.model.Taxi
import gyg.demo.mytaxitest.data.model.toLatLng
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapManager @Inject constructor() {

    private lateinit var map: GoogleMap

    //Init has to be called before interacting with the class
    fun init(map: GoogleMap, defaultPlace: Place) {
        this.map = map

        //To clear the default marker
        map.clear()

        val placeCenter = getPlaceCenter(defaultPlace)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeCenter, DEFAULT_ZOOM_LEVEL))
    }

    fun loadTaxis(list: List<Taxi>) {
        map.clear()

        for (taxi in list) {
            val marker = createTaxiMarker(taxi)
            map.addMarker(marker)
        }
    }

    fun loadTaxi(taxi: Taxi) {
        val marker = createTaxiMarker(taxi)
        map.addMarker(marker)
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                marker.position,
                SELECTED_ZOOM_LEVEL
            )
        )
    }

    fun getMapBounds(): LatLngBounds {
        return map.projection.visibleRegion.latLngBounds
    }

    private fun getPlaceCenter(place: Place): LatLng {
        LatLngBounds.Builder().apply {
            include(place.bound1.toLatLng())
            include(place.bound2.toLatLng())
            return build().center
        }
    }

    private fun createTaxiMarker(taxi: Taxi): MarkerOptions {
        return MarkerOptions()
            .position(taxi.coordinate.toLatLng())
            .icon(createTaxiMarkerIcon(taxi.type))
            .rotation(taxi.headingAngle)
    }

    private fun createTaxiMarkerIcon(taxiType: TaxiType): BitmapDescriptor {
        return BitmapDescriptorFactory.fromResource(
            when (taxiType) {
                TaxiType.NormalTaxi -> R.drawable.ic_vehicle_normal
                TaxiType.PoolingTaxi -> R.drawable.ic_vehicle_pooling
            }
        )
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL = 12f
        private const val SELECTED_ZOOM_LEVEL = 16f
    }
}
