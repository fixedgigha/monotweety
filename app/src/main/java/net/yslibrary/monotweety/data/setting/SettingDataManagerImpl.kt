package net.yslibrary.monotweety.data.setting

import com.f2prateek.rx.preferences.RxSharedPreferences
import rx.Completable
import rx.Observable

/**
 * Created by yshrsmz on 2016/09/29.
 */
class SettingDataManagerImpl(private val prefs: RxSharedPreferences) : SettingDataManager {

  private val notificationEnabled = prefs.getBoolean(NOTIFICATION_ENABLED, false)

  private val startOnRebootEnabled = prefs.getBoolean(START_ON_REBOOT_ENABLED, false)

  private val keepOpen = prefs.getBoolean(KEEP_OPEN, false)

  private val footerEnabled = prefs.getBoolean(FOOTER_ENABLED, false)
  private val footerText = prefs.getString(FOOTER_TEXT, "")

  override fun notificationEnabled(): Observable<Boolean> {
    return notificationEnabled.asObservable()
  }

  override fun notificationEnabled(enabled: Boolean) {
    notificationEnabled.set(enabled)
  }

  override fun startOnRebootEnabled(): Observable<Boolean> {
    return startOnRebootEnabled.asObservable()
  }

  override fun startOnRebootEnabled(enabled: Boolean) {
    startOnRebootEnabled.set(enabled)
  }

  override fun keepOpen(): Observable<Boolean> {
    return keepOpen.asObservable()
  }

  override fun keepOpen(enabled: Boolean) {
    keepOpen.set(enabled)
  }

  override fun footerEnabled(): Observable<Boolean> {
    return footerEnabled.asObservable()
  }

  override fun footerEnabled(enabled: Boolean) {
    footerEnabled.set(enabled)
  }

  override fun footerText(): Observable<String> {
    return footerText.asObservable()
  }

  override fun footerText(text: String) {
    footerText.set(text)
  }

  override fun clear(): Completable {
    return Completable.fromAction {
      notificationEnabled.delete()
      startOnRebootEnabled.delete()
      keepOpen.delete()
    }
  }

  companion object {
    const val NOTIFICATION_ENABLED = "notification_enabled"
    const val START_ON_REBOOT_ENABLED = "start_on_reboot_enabled"
    const val KEEP_OPEN = "keep_open"

    const val FOOTER_ENABLED = "footer_enabled"
    const val FOOTER_TEXT = "footer_text"
  }
}