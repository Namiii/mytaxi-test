package gyg.demo.mytaxitest.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import gyg.demo.mytaxitest.core.MainActivity
import gyg.demo.mytaxitest.taxiList.TaxiListFragment

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): TaxiListFragment
}