package tw.wesley.uiassignment

import android.app.Application
import timber.log.Timber

class AirQualityApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}