package com.easyshop.kotlinstudy.board

import com.easyshop.kotlinstudy.api.board.request.BoardRequest
import com.easyshop.kotlinstudy.api.board.response.BoardFindResponse
import com.easyshop.kotlinstudy.domain.board.BoardService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@SpringBootTest
class BoardServiceTest {


    @Autowired
    lateinit var boardService: BoardService


    @DisplayName("board 목록 조회")
    @Transactional
    @Test
    fun testGetBoards() {

        //Given 데이터 세팅
        val boardRequests = listOf(
            BoardRequest("테스트 게시판 입니다.", "심재경"),
            BoardRequest("테스트 코드 작성입니다", "테스트"),
            BoardRequest("테스트", "임재훈"),
            BoardRequest("테스트 타이틀", "김지웅"),
            BoardRequest("테스트 test test", "이지샵"),
        )

        //insert
        boardRequests.forEach { board ->
            saveBoard(board.title, board.writer)
        }

        //When 전체 조회
        val boardFindResponses = boardService.findAll()

        //Then
        assert(boardFindResponses.size == 5)
        assertTrue(
            boardFindResponses.containsAll(
                listOf(
                    BoardFindResponse(1, "테스트 게시판 입니다.", "심재경"),
                    BoardFindResponse(2, "테스트 코드 작성입니다", "테스트"),
                    BoardFindResponse(3, "테스트", "임재훈"),
                    BoardFindResponse(4, "테스트 타이틀", "김지웅"),
                    BoardFindResponse(5, "테스트 test test", "이지샵")
                )
            )
        )
    }

    @DisplayName("board 상세 조회")
    @Transactional
    @Test
    fun testGetBoardId() {

        //Given 데이터 세팅
        val (id, title, writer) = saveBoard("게시판 타이틀 입니다. 2025-02-12", "심재경")

        //When 단건 조회
        val boardFindResponses = boardService.findById(id)

        //Then
        assertNotNull(boardFindResponses)
        assertEquals(boardFindResponses.id, id)
        assertEquals(boardFindResponses.title, title)
        assertEquals(boardFindResponses.boardId, writer)
    }


    @DisplayName("board 수정")
    @Transactional
    @Test
    fun testUpdateBoardId() {

        //Given 데이터 세팅
        val (id) = saveBoard("게시판 타이틀 입니다. 2025-02-12", "심재경")
        val boardRequest = BoardRequest("게시판 입니다......", "업데이트")

        //When 업데이트 후 조회
        val boardFindResponses = boardService.updateBoard(id, boardRequest)
        val findResponse = boardService.findById(id)


        //Then
        assertNotNull(boardFindResponses)
        assertEquals(findResponse?.id, id)
        assertEquals(findResponse?.title, boardRequest.title)
        assertEquals(findResponse?.boardId, boardRequest.writer)
    }


    @DisplayName("board 삭제")
    @Transactional
    @Test
    fun testDeleteBoardId() {
        //Given 데이터 세팅
        val (id) = saveBoard("게시판 타이틀 입니다. 2025-02-12", "심재경")

        //When 삭제 요청 후 검색
        boardService.deleteBoard(id)
        val findResponse = boardService.findById(id)

        //Then
        assertNull(findResponse)
    }


    fun saveBoard(title: String, writer: String): BoardFindResponse {
        var boardRequest = BoardRequest(title, writer)

        return boardService.saveBoard(boardRequest)
    }


}