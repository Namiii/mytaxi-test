package gyg.demo.mytaxitest.di

import dagger.Component
import gyg.demo.mytaxitest.App
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RestModule::class, ActivityBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun restModule(restModule: RestModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}