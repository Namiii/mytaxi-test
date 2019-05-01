package gyg.demo.mytaxitest.core

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.data.model.TaxiType
import gyg.demo.mytaxitest.data.model.Vehicle

@BindingAdapter("setTaxiIcon")
fun setTaxiIcon(view: ImageView, vehicle: Vehicle?) {
    vehicle?.let {
        val colorFilterValue = when (vehicle.type) {
            is TaxiType.NormalTaxi -> {
                R.color.normal_taxi_icon_color
            }
            is TaxiType.PoolingTaxi -> {
                R.color.pooling_taxi_icon_color
            }
        }
        view.setTint(view.context, colorFilterValue)
        view.setImageResource(R.drawable.ic_vehicle)
    }
}

@BindingAdapter("setTaxiType")
fun setTaxiType(view: TextView, vehicle: Vehicle?) {
    vehicle?.let {
        val taxiTypeText = when (vehicle.type) {
            is TaxiType.NormalTaxi -> {
                R.string.normal_taxi_type
            }
            is TaxiType.PoolingTaxi -> {
                R.string.pooling_taxi_type
            }
        }
        view.setText(taxiTypeText)
    }
}

@BindingAdapter("goneIf")
fun goneIf(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}

fun ImageView.setTint(context: Context, @ColorRes colorId: Int) {
    val color = ContextCompat.getColor(context, colorId)
    val colorStateList = ColorStateList.valueOf(color)
    ImageViewCompat.setImageTintList(this, colorStateList)
}