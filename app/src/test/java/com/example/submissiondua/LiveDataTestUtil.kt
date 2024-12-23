package com.example.submissiondua

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import android.util.Log
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.awaitValueWithLogging(
    timeout: Long = 2,
    unit: TimeUnit = TimeUnit.SECONDS,
    onObserve: () -> Unit = {},
    logTag: String = "LiveDataTest"
): T {
    var result: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            result = value
            latch.countDown()
            Log.d(logTag, "LiveData updated with value: $value")
            this@awaitValueWithLogging.removeObserver(this)
        }
    }

    this.observeForever(observer)

    try {
        onObserve.invoke()
        Log.d(logTag, "Waiting for LiveData to be updated...")

        if (!latch.await(timeout, unit)) {
            throw TimeoutException("LiveData value was not set within the given time.")
        }
    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return result as T
}
