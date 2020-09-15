package top.xoai.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/31 9:21 下午
 */
public class FutrueDemoTest {
    public static void main(String[] args) {
        CompletableFuture<Long> f1 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2L;});
        CompletableFuture<Long> f2 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 3L;});
        CompletableFuture<Long> f3 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 4L;});
        CompletableFuture<Long> f4 = CompletableFuture.supplyAsync(()->{return 5L;});

        long start = System.currentTimeMillis();
//        System.out.println(start);
//        CompletableFuture.allOf(f1,f2,f3,f4);
//        String result = Stream.of(f1,f2,f3,f4).map(item-> {
//            String rs = "0L";
//            try {
//                rs = item.get().toString();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            return rs;
//        }).collect(Collectors.joining(""));
//        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(result);

        System.out.println(start);
        CompletableFuture.allOf(f1,f2,f3,f4);
        Long result = Stream.of(f1,f2,f3,f4).collect(Collectors.summingLong(item-> {
            Long longRs = 0L;
            try {
                longRs = item.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return longRs;
        }));
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(result);
    }
}
