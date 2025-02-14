package com.easyshop.kotlinstudy.domain.board

import org.springframework.data.jpa.repository.JpaRepository
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor

interface BoardRepository: JpaRepository<Board, Long>, KotlinJdslJpqlExecutor {
}