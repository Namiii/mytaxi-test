package gyg.demo.mytaxitest.core

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import gyg.demo.mytaxitest.R
import gyg.demo.mytaxitest.taxiList.TaxiListFragment
import gyg.demo.mytaxitest.taxiList.TaxiListFragmentInteractionListener
import gyg.demo.mytaxitest.taxiMap.startTaxiMapActivity


class MainActivity : BaseActivity() {

    private val taxiListFragment: TaxiListFragment by lazy { TaxiListFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startTaxiListFragment()
    }

    private fun startTaxiListFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_activity_container, taxiListFragment)
            .commit()

        taxiListFragment.listener = object : TaxiListFragmentInteractionListener {
            override fun taxiSelected(id: Int) {
                startTaxiMapActivity(id)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.map_option) {
            startTaxiMapActivity()
        }

        return super.onOptionsItemSelected(item)
    }
}
