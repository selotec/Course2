package lesson5;

public class Benchmark {
    static final int SIZE = 10000000;
    static final int H = SIZE / 2;
    float[] arr = new float[SIZE];

    void fillArray() {
        if(SIZE > 0) {
            this.arr[0] = 1;
        }

        // Копируем первый элемент в позицию 1, потом первые два элемента в 2 и 3, затем первые 4 элемента в 4, 5, 6, 7 и т.д.
        for(int i = 1; i < SIZE; i += i) {
            System.arraycopy(arr, 0, arr, i, ((SIZE - i) < i) ? (SIZE - i): i);
        }
    }

    void sequentialCalc() {
        fillArray();
        long startTime = System.currentTimeMillis();

        for(int i = 0; i < SIZE; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Последовательный расчет: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    void parallelCalc() {
        fillArray();
        long startTime = System.currentTimeMillis();

        final float[] firstHalf = new float[H];
        final float[] secondHalf = new float[H];

        // Располовинивание
        System.arraycopy(arr, 0, firstHalf, 0, H);
        System.arraycopy(arr, H, secondHalf, 0, H);

        // Расчет
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < H; i++) {
                    firstHalf[i] = (float)(firstHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < H; i++) {
                    secondHalf[i] = (float)(secondHalf[i] * Math.sin(0.2f + (H + i) / 5) * Math.cos(0.2f + (H + i) / 5) * Math.cos(0.4f + (H + i) / 2));
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Склейка
        System.arraycopy(firstHalf, 0, arr, 0, H);
        System.arraycopy(secondHalf, 0, arr, H, H);

        System.out.println("Параллельный расчет: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
