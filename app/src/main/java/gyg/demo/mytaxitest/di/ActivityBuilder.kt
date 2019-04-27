package gyg.demo.mytaxitest.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import gyg.demo.mytaxitest.ui.MainActivity
import gyg.demo.mytaxitest.ui.list.ListFragment

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment
}