package net.yslibrary.monotweety.base

import rx.Observable
import rx.Subscription
import rx.functions.Action1
import rx.subscriptions.SerialSubscription

/**
 * Created by yshrsmz on 2016/09/24.
 */
class SkipUntilCompletedAction1<T>(val doOnSubscribe: (T, () -> Unit) -> Unit) : Action1<T> {
  private var loading: Boolean = false
  override fun call(t: T) {
    if (loading) {
      return
    }
    loading = true
    doOnSubscribe(t) { loading = false }
  }
}

fun <T> Observable<T>.subscribeWhenCompleted(doOnSubscribe: (t: T, completed: () -> Unit) -> Unit): Subscription {
  return subscribe(SkipUntilCompletedAction1<T>(doOnSubscribe))
}

fun Subscription.setTo(subscription: SerialSubscription): Subscription {
  subscription.set(this)
  return this
}