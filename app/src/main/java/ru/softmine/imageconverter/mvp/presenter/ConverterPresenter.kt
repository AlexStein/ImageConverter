package ru.softmine.imageconverter.mvp.presenter

interface ConverterPresenter {
    fun convert(sourcePath: String, targetPath: String)
    fun cancel()
}