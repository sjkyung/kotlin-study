package com.easyshop.kotlinstudy.domain.board

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "BOARD")
class Board(
    @Column(name = "title")
    var title: String,

    @Column(name = "writer_id")
    var writer: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    var id: Long = 0L  // `@GeneratedValue`로 자동 생성되므로 기본값을 0으로 설정

    // 기본 생성자도 필요, 하지만 Kotlin에서는 기본적으로 제공
    constructor() : this("","")  // 기본 생성자 (예: 테스트용 기본 값 설정)\

    fun updateTitle(title: String, writer: String) {
        this.title = title
        this.writer = writer
    }
}