package com.easyshop.kotlinstudy.domain.board

import com.easyshop.kotlinstudy.api.board.request.BoardRequest
import com.easyshop.kotlinstudy.api.board.response.BoardFindResponse
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable

@Service
class BoardService(private val boardRepository: BoardRepository, private val boardJdslRepository: BoardJdslRepository) {

    /**
     * 전체 조회
     */
    @QueryMapping
    fun findAll(): List<BoardFindResponse> {
        return boardRepository
            .findAll()
            .mapNotNull { board ->
                BoardFindResponse(board.id, board.title, board.writer)
            }
    }

    /**
     * 상세 조회(단건)
     */
    fun findById(id: Int): BoardFindResponse? {
        val board = boardRepository
            .findById(id.toLong()).orElse(null)

        return board?.let { BoardFindResponse(board.id, board.title, board.writer) }
    }

    /**
     * board 저장
     */
    @MutationMapping
    @Transactional
    fun saveBoard(boardRequest: BoardRequest): BoardFindResponse {
        val board = Board(boardRequest.title, boardRequest.writer)
        return boardRepository.save(board).id?.let { id ->
            BoardFindResponse(id, board.title, board.writer)
        } ?: throw IllegalStateException("Failed save Board")
    }

    /**
     * board 수정
     */
    @MutationMapping
    @Transactional
    fun updateBoard(boardId: Int, boardRequest: BoardRequest) {
        val board = boardRepository
            .findById(boardId.toLong())
            .orElseThrow()
        board.updateTitle(boardRequest.title, boardRequest.writer);
    }

    /**
     * board 삭제
     */
    @MutationMapping
    fun deleteBoard(@PathVariable id: Int) {
        boardRepository.deleteById(id.toLong())
    }

    /**
     * jdsl repository 적용
     */
    fun getAllBoards(id: Int): List<BoardFindResponse> {
        return boardJdslRepository.reaToBoard(id.toLong());
    }

    /**
     * jdsl 신버전
     */
    fun getByFilter(id: Int): List<BoardFindResponse> {
        return boardRepository.findAll() {
            selectNew<BoardFindResponse>(
                path(Board::id),
                path(Board::title),
                path(Board::writer),
            ).from(
                entity(Board::class)
            ).where(
                path(Board::id).equal(id.toLong())
            )
        }.filterNotNull()
    }
}