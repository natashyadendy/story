package com.example.submissiondua

import android.os.Parcel
import android.os.Parcelable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import android.util.Log

@ExperimentalCoroutinesApi
class CoroutineDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher(), Parcelable {

    constructor(parcel: Parcel) : this(
        UnconfinedTestDispatcher()
    )

    override fun starting(description: Description) {
//        Log.d("CoroutineTest", "Test starting: ${description.displayName}")
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
//        Log.d("CoroutineTest", "Test finished: ${description.displayName}")
        Dispatchers.resetMain()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoroutineDispatcherRule> {
        override fun createFromParcel(parcel: Parcel): CoroutineDispatcherRule {
            return CoroutineDispatcherRule(parcel)
        }

        override fun newArray(size: Int): Array<CoroutineDispatcherRule?> {
            return arrayOfNulls(size)
        }
    }
}
