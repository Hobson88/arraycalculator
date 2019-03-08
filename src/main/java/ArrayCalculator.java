import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ArrayCalculator {

    private static <T> T getSilently(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    Integer sum(int[] array, int numbersOfWorkers) throws InterruptedException {

        int sizeOfPartArray = array.length / numbersOfWorkers;

        ExecutorService executorService = Executors.newCachedThreadPool();
//        ExecutorService executorService = Executors.newFixedThreadPool(numbersOfWorkers);

        Collection<Future<Integer>> partialSums = new ArrayList<>();
        for (int i = 0; i < numbersOfWorkers; i++) {
            int start = i * sizeOfPartArray;
            int endExclusively = start + sizeOfPartArray;
            Future<Integer> partialSumFuture = partFuture(array, start, endExclusively, executorService);
            partialSums.add(partialSumFuture);
        }

        System.out.println("Doing something important for 5 seconds");
        Thread.sleep(5000);

        int sum = partialSums.stream().mapToInt(ArrayCalculator::getSilently).sum();
        executorService.shutdown();
        return sum;
    }

    private Future<Integer> partFuture(int[] array, int start, int endExclusively, ExecutorService executorService) {
        return executorService.submit(() -> sumPart(array, start, endExclusively));
    }

    private int sumPart(int[] originalArray, int start, int endExclusively) {
        int sum = 0;
        for (int i = start; i < endExclusively; i++) {
            sum += originalArray[i];
        }
        return sum;
    }
}
