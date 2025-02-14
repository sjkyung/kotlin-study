package com.easyshop.kotlinstudy.domain.board

import com.easyshop.kotlinstudy.api.board.request.BoardRequest
import com.easyshop.kotlinstudy.api.board.response.BoardFindResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable

@Service
class BoardService(private val boardRepository: BoardRepository, private val boardJdslRepository: BoardJdslRepository) {

    fun findAll(): List<BoardFindResponse> {
        return boardRepository
            .findAll()
            .mapNotNull { board ->
                BoardFindResponse(board.id, board.title, board.writer)
            }
    }

    fun findById(id: Int): BoardFindResponse? {
        val board = boardRepository
            .findById(id.toLong()).orElse(null)

        return board?.let { BoardFindResponse(board.id, board.title, board.writer) }
    }


    fun saveBoard(boardRequest: BoardRequest): BoardFindResponse {
        val board = Board(boardRequest.title, boardRequest.writer)
        return boardRepository.save(board).id?.let { id ->
            BoardFindResponse(id, board.title, board.writer)
        } ?: throw IllegalStateException("Failed save Board")
    }

    @Transactional
    fun updateBoard(boardId: Int, boardRequest: BoardRequest) {
        val board = boardRepository
            .findById(boardId.toLong())
            .orElseThrow()
        board.updateTitle(boardRequest.title, boardRequest.writer);
    }

    fun deleteBoard(@PathVariable id: Int) {
        boardRepository.deleteById(id.toLong())
    }

    fun getAllBoards(id: Int): List<BoardFindResponse> {
        return boardJdslRepository.reaToBoard(id.toLong());
    }

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