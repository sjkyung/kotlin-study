package com.easyshop.kotlinstudy;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {

    public static void main(String[] args)throws ExecutionException, InterruptedException {

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task 1 executed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


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


        CompletableFuture<Integer> combinedFuture = CompletableFuture.supplyAsync(() -> {
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            return 20;
        }), (result1, result2) -> {
            return result1 + result2;
        });


        CompletableFuture<Integer> futureWithErrorHandling = CompletableFuture.supplyAsync(() -> {
            int result = 10 / 0;
            return result;
        }).exceptionally(ex -> {
            System.out.println("Handled exception" + ex.getMessage());
            return -1;
        });


        CompletableFuture<String> futureChaining = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        }).thenApply(result -> {
            return result + "World";
        });


        CompletableFuture<Void> completableFutureAllof = CompletableFuture.allOf(future, future2, combinedFuture);

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
