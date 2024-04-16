package com.sookmyung.umbrellafriend.data.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    // 클라우드 서버에 등록되었을 때 호출
    // 파라미터로 전달된 token 이 앱을 구분하기 위한 고유한 키가 된다
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // token 을 서버로 전송

    }

    // 클라우드 서버에서 메시지를 전송하면 자동으로 호출
    // 이 메서드 안에서 메시지를 처리하여 사용자에게 알림을 보내거나 할 수 있다
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // 수신한 메시지를 처리
    }
}