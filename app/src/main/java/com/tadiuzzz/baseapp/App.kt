package com.tadiuzzz.baseapp

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.facebook.stetho.Stetho
import io.fabric.sdk.android.Fabric
import com.tadiuzzz.baseapp.di.component.AppComponent
import com.tadiuzzz.baseapp.di.component.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        configure()
    }

    private fun configure() {
        setupDI()
        setupLogging()
        setupStetho()
//        setupFirebase()
    }

    /**
     * Инициализация движка внедрения зависимостей
     */
    private fun setupDI() {
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }

    /**
     * Инициализация Stetho в debug-сборках
     */
    private fun setupStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    /**
     * Настройка логирования
     * В debug-сборках - сообщения логируются локально
     * В release-сборках - логируются сообщения выбранных уровней (WARN, ERROR) в Crashlytics
     */
    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                        return
                    }
                    Crashlytics.log(priority, tag, message)
                    if (t != null && priority == Log.ERROR)
                        Crashlytics.logException(t)
                }
            })
        }
    }

    /**
     * Настройка Firebase Crashlytics
     * Crashlytics отключен в debug-сборках
     */
    private fun setupFirebase() {
        val crashlyticsCore = CrashlyticsCore.Builder()
            .disabled(BuildConfig.DEBUG)
            .build()
        Fabric.with(this, Crashlytics.Builder().core(crashlyticsCore).build())
    }

}