package kwony.allweather.arch

import android.os.Looper
import androidx.lifecycle.*
import io.reactivex.Observable

val ALWAYS_ON get() =  object: LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this).apply {
            this.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            this.handleLifecycleEvent(Lifecycle.Event.ON_START)
            this.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }
}

fun <T> LiveData<T>.toObservable(): Observable<T> {
    return Observable.fromPublisher<T> {
        LiveDataReactiveStreams.toPublisher(ALWAYS_ON, this)
    }
}

fun <T> MutableLiveData<T>.notifyChange() {
    this.setValueSafely(this.value)
}

fun <T> MutableLiveData<T>.setValueSafely(value: T?) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        setValue(value)
    } else {
        postValue(value)
    }
}