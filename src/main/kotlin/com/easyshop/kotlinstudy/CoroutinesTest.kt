package com.easyshop.kotlinstudy

import kotlinx.coroutines.*
import kotlin.system.exitProcess

/**
 * 코루틴 예제 코드
 */
class CoroutinesTest {

    suspend fun runAsyncTasks() = coroutineScope {
        //결과가 없는 비동기
        val future = launch {
            try {
                delay(2000)
                println("Task 1 executed")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        //결과가 있는 비동기
        val future2 = async {
            try {
                delay(2000)
                println("Task 2 executed")
                42
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        //두 비동기 결과 결합
        val combinedFuture = async {
            val result = 10
            val result2 = 20
            result + result2
        }


        //예외 처리
        val futureWithErrorHandling = async {
            try {
                val result = 10 / 0
                result
            }catch (e:Exception){
                e.printStackTrace()
                -1
            }
        }

        //비동기 연결해서 비동기 처리
        val futureChaining = async {
            "Hello".also {
                delay(1000)
            } + "World"
        }

        //실행이 모두 끝날때 까지 호출부 코루틴을 일시중단
        joinAll(future,future2,combinedFuture,futureWithErrorHandling,futureChaining)


        println("future2 ::: ${future2.await()}")
        println("combine ::: result ${combinedFuture.await()}")
        println("future with exception handling result ::: ${futureWithErrorHandling.await()}")
        println("chained result ::: ${futureChaining.await()}")


    }

    // main 함수는 Nothing 반환타입으로 설정하고, exitProcess(0)으로 종료
    fun main(): Nothing {
        runBlocking {
            val coroutinesTest = CoroutinesTest()
            coroutinesTest.runAsyncTasks()  // 비동기 작업 실행
        }
        exitProcess(0)  // 프로그램 종료
    }
}

fun main() {
    CoroutinesTest().main()
}