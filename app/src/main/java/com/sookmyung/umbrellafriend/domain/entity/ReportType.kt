package com.sookmyung.umbrellafriend.domain.entity

enum class ReportType(type:String) {
    MISSING( "분실"),
    QR( "QR"),
    BROKEN( "파손"),
    ETC( "기타"),
    NONE("미선택")
}