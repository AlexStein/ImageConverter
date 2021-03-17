package ru.softmine.imageconverter.converters

import android.graphics.Bitmap
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import java.io.InputStream
import java.io.OutputStream

interface ImageConverter {
    fun load(uri: String): InputStream
    fun convertImage(bitmap: Bitmap, targetPath: String): @NonNull Single<Boolean>
    fun save(uri: String): OutputStream
}