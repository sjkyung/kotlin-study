package com.easyshop.kotlinstudy.api.board.response

data class BoardFindResponse(
    val id: Long,
    val title: String,
    val writer: String
) {
}