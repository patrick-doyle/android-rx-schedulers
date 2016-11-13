#!/usr/bin/env bash
./gradlew clean test
./gradlew :rx-android-schedulers:build :rx-android-schedulers:bintrayUpload
./gradlew :rx2-android-schedulers:build :rx2-android-schedulers:bintrayUpload