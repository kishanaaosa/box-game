package com.example.boxgame.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snack bar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val observers = CopyOnWriteArraySet<ObserverWrapper<in T?>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, observer)
    }

    override fun removeObservers(owner: LifecycleOwner) {
        observers.clear()
        super.removeObservers(owner)
    }

    override fun removeObserver(observer: Observer<in T?>) {
        observers.remove(observer)
        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    private class ObserverWrapper<T>(private val observer: Observer<T?>) : Observer<T?> {

        private val pending = AtomicBoolean(false)


        fun newValue() {
            pending.set(true)
        }

        override fun onChanged(value: T?) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value)

            }
        }
    }
}