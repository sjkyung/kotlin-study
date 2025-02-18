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

    /**
     * 상세 조회 API
     */
    @GetMapping("/boards/{id}")
    fun getBoard(@PathVariable id: Int): ResponseEntity<BoardFindResponse> {
        return ResponseEntity.ok(boardService.findById(id))
    }

    /**
     * 저장 API
     */
    @PostMapping("/boards")
    fun testPostBoards(@RequestBody boardRequest: BoardRequest): ResponseEntity<BoardFindResponse> {
        return ResponseEntity.ok(boardService.saveBoard(boardRequest))
    }

    /**
     * 수정 API
     */
    @PutMapping("/boards/{id}")
    fun testPutBoard(@PathVariable id: Int, @RequestBody boardRequest: BoardRequest) {
        return boardService.updateBoard(id, boardRequest)
    }

    /**
     * 삭제 조회 API
     */
    @DeleteMapping("/boards/{id}")
    fun testDeleteBoard(@PathVariable id: Int) {
        return boardService.deleteBoard(id)
    }

    /**
     * JDSL 조회 API
     */
    @GetMapping("/boards1/{id}")
    fun testGetBoards1(@PathVariable id: Int): List<BoardFindResponse> {
        return boardService.getAllBoards(id);
    }

    /**
     * JDSL 조회 API
     */
    @GetMapping("/boards2/{id}")
    fun testGetBoards2(@PathVariable id: Int): List<BoardFindResponse> {
        return boardService.getByFilter(id);
    }
}