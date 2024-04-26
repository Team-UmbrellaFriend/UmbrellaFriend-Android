package com.sookmyung.umbrellafriend.domain.entity

enum class ReportType(type: String) {
    MISSING("분실"),
    QR("QR"),
    BROKEN("파손"),
    ETC("기타"),
    NONE("미선택")
}

fun ReportType.getType(): String {
    return when (this) {
        ReportType.MISSING -> "분실"
        ReportType.QR -> "QR"
        ReportType.BROKEN -> "파손"
        ReportType.ETC -> "기타"
        ReportType.NONE -> "기타"
    }
}