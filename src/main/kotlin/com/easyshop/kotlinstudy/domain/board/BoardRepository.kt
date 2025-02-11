package com.easyshop.kotlinstudy.domain.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Long> {
}