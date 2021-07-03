package ru.softmine.imageconverter.mvp.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.softmine.imageconverter.mvp.model.ImageConverterImpl
import ru.softmine.imageconverter.mvp.view.MainView
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MainPresenter : MvpPresenter<MainView>(), ConverterPresenter {

    private var converterDisposable: CompositeDisposable? = null

    override fun convert(sourcePath: String, targetPath: String) {
        val converter = ImageConverterImpl()
        converterDisposable = CompositeDisposable()

        val stream: InputStream
        try {
            stream = converter.load(sourcePath)
        } catch (e: IOException) {
            cancel()
            viewState.onError()
            return
        }

        val bitmap: Bitmap = BitmapFactory.decodeStream(stream)

        converterDisposable?.add(
            converter.convertImage(bitmap, targetPath)
                .delay(3, TimeUnit.SECONDS)
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.onComplete()
                }, {
                    viewState.onError()
                })
        )
    }

    override fun cancel() {
        converterDisposable?.dispose()
    }
}