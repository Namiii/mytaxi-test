package gyg.demo.mytaxitest.taxiMap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import gyg.demo.mytaxitest.data.model.Place
import gyg.demo.mytaxitest.data.model.Vehicle
import gyg.demo.mytaxitest.data.model.toLatLng
import javax.inject.Singleton

@Singleton
class MapManager {

    private lateinit var map: GoogleMap

    //Init has to be called before interacting with the class
    fun init(map: GoogleMap) {
        this.map = map
        map.moveCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM_LEVEL))
    }

    fun loadTaxis(list: List<Vehicle>) {
        for (taxi in list) {
            val marker = createTaxiMarker(taxi)
            map.addMarker(marker)
        }
    }

    fun zoomInOnTaxi(taxi: Vehicle) {
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

    fun getPlaceCenter(place: Place): LatLng {
        LatLngBounds.Builder().apply {
            include(place.bound1.toLatLng())
            include(place.bound2.toLatLng())
            return build().center
        }
    }

    private fun createTaxiMarker(vehicle: Vehicle): MarkerOptions {
        return MarkerOptions()
            .position(vehicle.coordinate.toLatLng())
            .rotation(vehicle.headingAngle)
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL = 12f
        private const val SELECTED_ZOOM_LEVEL = 18f
    }
}