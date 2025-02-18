package com.easyshop.kotlinstudy.domain.board


import com.easyshop.kotlinstudy.api.board.response.BoardFindResponse
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class BoardJdslRepository(
    boardRepository: BoardRepository,
    private val entityManager: EntityManager,
    private val queryFactory: SpringDataQueryFactory
) {

    /**
     * jdsl 적용
     */
    fun reaToBoard(id: Long): List<BoardFindResponse> {
        val context = JpqlRenderContext()

        val jpql = jpql {
            selectNew<BoardFindResponse>(
                path(Board::id),
                path(Board::title),
                path(Board::writer)
            )
                .from(entity(Board::class))
                .where(
                    path(Board::id).equal(id)
                )
        }

        val render = JpqlRenderer().render(jpql, context)
        println(render)
        val resultList = entityManager.createQuery(render.query).apply {
            render.params.forEach { (name, value) ->
                setParameter(name, value)
            }
        }.resultList

        return resultList.map {
            val obj = it as Array<*>
            BoardFindResponse(
                id = obj[0] as Long,
                title = obj[1] as String,
                writer = obj[2] as String
            )
            /*         val jpaQuery: Query = entityManager.createQuery(jpql, context)
            return  resultList;*/
        }
    }

    fun findByTitle(id: Long): List<BoardFindResponse> {
        return queryFactory.listQuery<BoardFindResponse> {
            selectMulti(
                col(Board::id),
                col(Board::title),
                col(Board::writer)
            )
            from(entity(Board::class))
            where(
                col(Board::id).equal(id),
            )
        }
    }

}