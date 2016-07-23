package com.twistedequations.benchmark

import com.twistedequations.mvl.rx.DefaultAndroidSchedulers
import com.twistedequations.mvl.rx.DefaultRxSchedulers
import com.twistedequations.mvl.rx.Priority
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import rx.Completable
import rx.Observable
import java.io.File


class RxJavaBenchmark {

    @Rule
    @JvmField
    val folder = TemporaryFolder()

    lateinit var dataFile : File
    lateinit var imageFile : File

    @Before
    fun setUp() {
        dataFile = folder.newFile("data.json")
        dataFile.parentFile.mkdirs()
        imageFile = folder.newFile("image.png")
        imageFile.parentFile.mkdirs()

        val dataFileStream = dataFile.outputStream()
        javaClass.getResourceAsStream("/benchmark/test_post.json").copyTo(dataFileStream)
        dataFileStream.close()

        val imageFileStream = imageFile.outputStream()
        javaClass.getResourceAsStream("/benchmark/test_image.png").copyTo(imageFileStream)
        imageFileStream.close()
    }

    @Test
    fun defaultBenchmarkThreading() {
        //Load the data and the image 10 times

        val schedulers = DefaultRxSchedulers()
        val imageObservable = Observable.from(1..10)
                .doOnNext { SlowInputStream(imageFile.inputStream()).readBytes() }
                .map { "Image $it read \n" }
                .doOnNext { print(it) }
                .subscribeOn(schedulers.network())

        val dataObservable = Observable.from(1..3)
                .doOnNext { SlowInputStream(dataFile.inputStream()).readBytes() }
                .map { "Data $it read \n" }
                .doOnNext { print(it) }
                .subscribeOn(schedulers.io())

        val completeObservable = Observable.merge(imageObservable, dataObservable)

        Completable.fromObservable(completeObservable).await()
    }

    @Test
    fun androidBenchmarkThreading() {
        //Load the data and the image 10 times

        val schedulers = DefaultAndroidSchedulers()
        val imageObservable = Observable.from(1..10)
                .doOnNext { SlowInputStream(imageFile.inputStream()).readBytes() }
                .map { "Image $it read \n" }
                .doOnNext { print(it) }
                .subscribeOn(schedulers.network(Priority.LOW))

        val dataObservable = Observable.from(1..3)
                .doOnNext { SlowInputStream(dataFile.inputStream()).readBytes() }
                .map { "Data $it read \n" }
                .doOnNext { print(it) }
                .subscribeOn(schedulers.network(Priority.HIGH))

        val completeObservable = Observable.merge(imageObservable, dataObservable)

        Completable.fromObservable(completeObservable).await()
    }
}