import java.util.Arrays;
import java.util.TreeSet;

public class ArmstrongNumbers {
    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 153, 370, 371, 407, 1634, 8208, 9474,
    // 54 748,
    // 92 727,
    // 93 084,
    // 548 834,
    // 1 741 725,
    // 4 210 818,
    // 9 800 817,
    // 9 926 315,
    // 24 678 050,
    // 24 678 051,
    // 88 593 477,
    // 146 511 208,
    // 472 335 975,
    // 534 494 836,
    // 912 985 153,
    // 4 679 307 774 ...

    public static long[][] fillArrayDegrees() {
        long[][] array = new long[10][19];
        for (int i = 0; i < 10; i++) {// число
            for (int j = 1; j < 20; j++) {// степень
                long result = i;// число, которое будем возводить в степень
                for (int k = 1; k < j; k++) {
                    result *= i;
                }
                array[i][j - 1] = result;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        long x = System.currentTimeMillis();
        long[] result = getNumbers(Long.MAX_VALUE);
        System.out.println(Arrays.toString(result));
        System.out.println(result.length);
        long y = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (y - x) / 1000);

        long a = System.currentTimeMillis();
        long[] result2 = getNumbers(10_000);
        System.out.println(Arrays.toString(result2));
        System.out.println(result2.length);
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);
    }

    public static long[] getNumbers(long N) {// 1000
        if(N <= 0) return new long[0];
        long[][] arrayDegrees = fillArrayDegrees();// заполняем двумерный массив степенями
        TreeSet<Long> listResult = new TreeSet<>();// создаем список результатов вычислений
        //long[] intArray = {0, 4, 4, 7, 9};// создаем массив длинною в требуемое число
        long[] intArray =  new long[String.valueOf(N).length()];// создаем массив длинною в требуемое число
        Arrays.fill(intArray, 9);// заполняем созданный массив 9ками
        mainCalculate(arrayDegrees,  intArray, N, listResult);// основные вычисления происходят здесь
        while (decrementArray(intArray)) {
            mainCalculate(arrayDegrees, intArray, N, listResult);
        }
        return listResult.stream().mapToLong(Long::longValue).toArray();
    }
    public static void mainCalculate(long[][] arrayDegrees, long[] intArray, long N, TreeSet<Long> listResult) {
        long result = resultSum(arrayDegrees, intArray);
        if (result < 0) return;
        long[] resultNumbers = resultToLongArray(result);
        Arrays.sort(intArray);
        Arrays.sort(resultNumbers);
        if (Arrays.equals(intArray, resultNumbers) && result < N) listResult.add(result);
        else {
            if (Arrays.stream(intArray).anyMatch(i -> i == 0)) {
                long[] newInitArray = Arrays.copyOfRange(intArray, 1, intArray.length);
                mainCalculate(arrayDegrees, newInitArray, N, listResult);
            }
        }
    }
    public static long resultSum(long[][] arrayDegrees, long[] intArray) {// получаем сумму всех цифр, возведенных в степень длины массива
        long sum = 0;
        for (int i = 0; i < intArray.length; i++) {
            long x = arrayDegrees[(int) intArray[i]][intArray.length - 1];
            long result = sum + x;
            if (result < sum) return x;
            else sum = result;
        }
        return sum;
    }
    public static long[] resultToLongArray(long sum) {// раскладываем полученную сумму на массив чисел
        char[] charArray = String.valueOf(sum).toCharArray();
        long[] resultNumbers = new long[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            resultNumbers[i] = charArray[i] - '0';
        }
        return resultNumbers;
    }
    private static boolean decrementArray(long[] intArray) {// получение уникального массива, посредством уменьшения на 1
        int index = 0;
        while (index < intArray.length && intArray[index] == 0) index++;
        if (index + 1 == intArray.length && intArray[index] == 1) return false;
        Arrays.fill(intArray, 0, index + 1, intArray[index] - 1);
        return true;
    }
}