package ru.softmine.imageconverter.mvp.model

import android.graphics.Bitmap
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import ru.softmine.imageconverter.converters.ImageConverter
import java.io.*

class ImageConverterImpl : ImageConverter {

    override fun load(uri: String): InputStream {
        val initialFile = File(uri)
        return FileInputStream(initialFile)
    }

    override fun convertImage(bitmap: Bitmap, targetPath: String): @NonNull Single<Boolean> {
        return Single.fromCallable {
            val imageOutputStream = save(targetPath)

            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutputStream)) {
                return@fromCallable true
            } else {
                throw Exception("Conversion problem")
            }
        }
    }

    override fun save(uri: String): OutputStream {
        return FileOutputStream(uri)
    }
}