package com.sookmyung.umbrellafriend.util

import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

fun AppCompatActivity.initBackPressedCallback() {
    var onBackPressedTime = 0L
    val deadline = 2000L
    onBackPressedDispatcher.addCallback(this) {
        if (System.currentTimeMillis() - onBackPressedTime >= deadline) {
            onBackPressedTime = System.currentTimeMillis()
            toast("한 번 더 누르면 앱을 종료합니다.") //TODO string으로 추출
        } else {
            this@initBackPressedCallback.finishAffinity()
            System.runFinalization()
            exitProcess(0)
        }
    }
}
