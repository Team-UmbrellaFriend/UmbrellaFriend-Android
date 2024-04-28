package com.sookmyung.umbrellafriend.domain.entity

enum class WithdrawType(type: String) {
    QUANTITY("수량"),
    MANAGEMENT("QR"),
    NEW_ACCOUNT("파손"),
    ETC("기타"),
    NONE("미선택")
}

fun WithdrawType.getType(): String {
    return when (this) {
        WithdrawType.QUANTITY -> "수량"
        WithdrawType.MANAGEMENT -> "관리"
        WithdrawType.NEW_ACCOUNT -> "새계정"
        WithdrawType.ETC -> "기타"
        WithdrawType.NONE -> "기타"
    }
}