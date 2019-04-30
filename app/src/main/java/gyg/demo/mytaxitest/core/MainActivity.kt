package gyg.demo.mytaxitest.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.taxiList.TaxiListFragment

class MainActivity : AppCompatActivity() {

    private val taxiListFragment: TaxiListFragment by lazy { TaxiListFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        startTaxiListFragment()
    }

    private fun startTaxiListFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_activity_container, taxiListFragment)
            .commit()
    }
}
