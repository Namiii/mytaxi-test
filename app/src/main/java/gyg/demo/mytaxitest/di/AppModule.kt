package gyg.demo.mytaxitest.di

import android.app.Application
import dagger.Module
import dagger.Provides
import gyg.demo.mytaxitest.providers.BaseScheduleProvider
import gyg.demo.mytaxitest.providers.ScheduleProvider
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providerScheduleProvider(): BaseScheduleProvider =
        ScheduleProvider()
}
