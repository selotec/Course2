package lesson2;

public class Hw2 {
    static final int DIMENSION = 4;

    public static void main(String[] args) {
        // Полностью корректный по заданию массив, ожидаем сумму, равную 16
        String[][] correctArray1 = {
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"}};
        // Третья строка не соответствует заданной размерности, ожидаем MyArraySizeException
        String[][] incorrectArray1 = {
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1"},
                {"1", "1", "1", "1"}};
        // Не хватает одной строки, ожидаем MyArraySizeException
        String[][] incorrectArray2 = {
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"}};
        // Массив пустой, ожидаем MyArraySizeException
        String[][] incorrectArray3 = {};
        // Символы в ячейке 3 3, ожидаем ArrayDataException
        String[][] incorrectArray4 = {
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "один"}};

        // Просто сложили все тесты в один массив, чтоб удобно было прокрутить их в цикле
        String[][][] testArrays = {correctArray1, incorrectArray1, incorrectArray2, incorrectArray3, incorrectArray4};

        for(int i = 0; i < testArrays.length; i++) {
            System.out.println("Тестовая матрица " + (i + 1));
            try {
                System.out.println("Сумма всех элементов матрицы равна: " + matrixSum(testArrays[i]));
            } catch (MyArraySizeException | ArrayDataException e) {
                System.out.println("Перехвачено исключение " + e.getClass().getName());
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    public static int matrixSum(String[][] matrix) {
        int sum = 0;

        /*
            Колебался, как сделать - в цикле сначала проверить размерность и потом конвертировать в отдельном цикле,
            или обойтись одним циклом, в котором и размерность проверять на ходу и конвертацию делать.
            Буду благодарен за совет, как все таки было б сделать лучше.

            С одной стороны один цикл вместо двух, с другой, если размерность неправильная, можно не тратить
            время и память на преобразование и суммирование. И вроде бы читается код с двумя раздельными циклами лучше,
            т.к. код поделен на логические блоки, но первый вариант компактнее.
            По итогу остановился на варианте с отдельными циклами на проверку размерности и конвертацию.
         */
        if(matrix.length != DIMENSION) {
            throw new MyArraySizeException("Количество строк не равно " + DIMENSION + "!");
        }
        for(int i = 0; i < matrix.length; i++) {
            if(matrix[i].length != DIMENSION) {
                throw new MyArraySizeException("Количество значений в строке " + i + " не равно " + DIMENSION + "!");
            }
        }

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                try {
                    int castedToInt = Integer.parseInt(matrix[i][j]);
                    sum += castedToInt;
                }
                catch (NumberFormatException e) {
                    throw new ArrayDataException("Не удалось преобразовать ячейку " + i + " " + j + " в число!", e);
                }
            }
        }

        return sum;
    }
}