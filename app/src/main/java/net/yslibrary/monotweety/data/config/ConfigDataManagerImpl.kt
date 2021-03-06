package net.yslibrary.monotweety.data.config

import com.twitter.sdk.android.core.models.Configuration
import net.yslibrary.monotweety.base.Clock
import net.yslibrary.monotweety.data.config.local.ConfigLocalDataManager
import net.yslibrary.monotweety.data.config.remote.ConfigRemoteDataManager
import rx.Observable
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions
import timber.log.Timber

/**
 * Created by yshrsmz on 2016/10/01.
 */
class ConfigDataManagerImpl(private val remoteDataManager: ConfigRemoteDataManager,
                            private val localDataManager: ConfigLocalDataManager,
                            private val clock: Clock) : ConfigDataManager {

  private var subscription = Subscriptions.unsubscribed()

  override fun shortUrlLengthHttps(): Observable<Int> {
    return localDataManager.shortUrlLengthHttps()
        .doOnNext {
          if (subscription.isUnsubscribed && localDataManager.outdated()) {
            Timber.d("fetch new config from api")
            subscription = remoteDataManager.get()
                .subscribeOn(Schedulers.io())
                .doOnSuccess { updateConfig(it) }
                .doOnError { Timber.e(it, it.message) }
                .subscribe()
          }
        }
  }

  private fun updateConfig(configuration: Configuration) {
    Timber.d("update local config: $configuration")
    localDataManager.shortUrlLengthHttps(configuration.shortUrlLengthHttps)
    localDataManager.updatedAt(clock.currentTimeMillis())
  }
}