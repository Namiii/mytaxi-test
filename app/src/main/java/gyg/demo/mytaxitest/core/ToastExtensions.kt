package gyg.demo.mytaxitest.core

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

fun Context.makeLongToast(text: String) {
    val toast = Toast.makeText(this, text, Toast.LENGTH_LONG)
    val view = toast.view.findViewById<TextView>(android.R.id.message)
    view.gravity = Gravity.CENTER
    toast.show()
}
