package com.easyshop.kotlinstudy.api.board

import com.easyshop.kotlinstudy.api.board.request.BoardRequest
import com.easyshop.kotlinstudy.api.board.response.BoardFindResponse
import com.easyshop.kotlinstudy.domain.board.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class BoardController(private val boardService: BoardService) {


    /**
     * 전체 조회 API
     */
    @GetMapping("/boards")
    fun testGetBoards(): ResponseEntity<List<BoardFindResponse>> {
        return ResponseEntity.ok(boardService.findAll())
    }

    @GetMapping("/boards/{id}")
    fun getBoard(@PathVariable id: Int): ResponseEntity<BoardFindResponse> {
        return ResponseEntity.ok(boardService.findById(id))
    }

    @PostMapping("/boards")
    fun testPostBoards(@RequestBody boardRequest: BoardRequest): ResponseEntity<BoardFindResponse> {
        return ResponseEntity.ok(boardService.saveBoard(boardRequest))
    }

    @PutMapping("/boards/{id}")
    fun testPutBoard(@PathVariable id: Int, @RequestBody boardRequest: BoardRequest) {
        return boardService.updateBoard(id, boardRequest)
    }

    @DeleteMapping("/boards/{id}")
    fun testDeleteBoard(@PathVariable id: Int) {
        return boardService.deleteBoard(id)
    }


}