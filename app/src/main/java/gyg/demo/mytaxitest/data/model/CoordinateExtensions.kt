package gyg.demo.mytaxitest.data.model

import com.google.android.gms.maps.model.LatLng

fun Coordinate.toLatLng(): LatLng {
    return LatLng(lat, long)
}
