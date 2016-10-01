package net.yslibrary.monotweety

import dagger.Subcomponent
import net.yslibrary.monotweety.base.di.UserScope
import net.yslibrary.monotweety.data.UserDataModule
import net.yslibrary.monotweety.setting.domain.NotificationEnabledManager
import net.yslibrary.monotweety.status.domain.CheckStatusLength
import net.yslibrary.monotweety.status.domain.UpdateStatus

/**
 * Created by yshrsmz on 2016/09/24.
 */
@UserScope
@Subcomponent(
    modules = arrayOf(UserDataModule::class)
)
interface UserComponent : AppModule.Provider {

  fun notificationEnabledManager(): NotificationEnabledManager

  fun checkStatusLength(): CheckStatusLength

  fun updateStatus(): UpdateStatus

  interface ComponentProvider {
    fun userComponent(): UserComponent
  }
}