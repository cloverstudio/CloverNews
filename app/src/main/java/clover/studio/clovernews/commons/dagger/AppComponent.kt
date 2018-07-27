package clover.studio.clovernews.commons.dagger

import clover.studio.clovernews.commons.CloverNewsApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        BuildersModule::class,
        AppModule::class))
interface AppComponent {
    fun inject(app: CloverNewsApp)
}
