package gyg.demo.mytaxitest.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import gyg.demo.mytaxitest.core.MainActivity
import gyg.demo.mytaxitest.taxiList.TaxiListFragment
import gyg.demo.mytaxitest.taxiMap.TaxiMapActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): TaxiListFragment

    @ContributesAndroidInjector
    abstract fun contributeTaxiMapActivity(): TaxiMapActivity

}
