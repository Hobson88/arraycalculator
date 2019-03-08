public class Example {
    public static void main(String[] args) throws InterruptedException {
        ArrayCalculator calculator = new ArrayCalculator();
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer sum = calculator.sum(array, 5);
        System.out.println(sum);
    }
}
