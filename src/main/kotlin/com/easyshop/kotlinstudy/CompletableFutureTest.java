package com.easyshop.kotlinstudy;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * completableFuture 예저 코드
 */
public class CompletableFutureTest {

    public static void main(String[] args)throws ExecutionException, InterruptedException {

        //callback 없는 비동기
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task 1 executed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //callback 있는 비동기
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task 2 executed");
                return 42;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        //비동기 연결 combine
        CompletableFuture<Integer> combinedFuture = CompletableFuture.supplyAsync(() -> {
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            return 20;
        }), (result1, result2) -> {
            return result1 + result2;
        });

        //에러 처리
        CompletableFuture<Integer> futureWithErrorHandling = CompletableFuture.supplyAsync(() -> {
            int result = 10 / 0;
            return result;
        }).exceptionally(ex -> {
            System.out.println("Handled exception" + ex.getMessage());
            return -1;
        });

        //콜백 받고 이어서 비동기 처리
        CompletableFuture<String> futureChaining = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        }).thenApply(result -> {
            return result + "World";
        });

        //전체 비동기가 끝날때 콜백
        CompletableFuture<Void> completableFutureAllof = CompletableFuture.allOf(future, future2, combinedFuture);

        //하나의 비동기만 끝나도 콜백
        CompletableFuture<Object> completableFutureAnyof = CompletableFuture.anyOf(future, future2, combinedFuture);


        //future.get();
        System.out.println("future 2 ::::: "+future2.get());
        System.out.println("Combine ::: result  "+combinedFuture.get());
        //System.out.println("Future with exception handling result: " + futureWithErrorHandling.get());
        //System.out.println("Chained result: " + futureChaining.get());


        //completableFutureAllof.get();
        //System.out.println("AnyOf result: " + completableFutureAnyof.get());
    }
}
