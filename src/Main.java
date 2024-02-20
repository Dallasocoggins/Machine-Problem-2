//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();

        int[][] matrixA = new int[20][20];
        for(int i = 0; i < matrixA.length; i++){
            for(int j = 0; j < matrixA[i].length; j++) {
                matrixA[i][j] = rand.nextInt(10);
            }
        }

        int[][] matrixB = new int[20][20];
        for(int i = 0; i < matrixB.length; i++){
            for(int j = 0; j < matrixB[i].length; j++) {
                matrixB[i][j] = rand.nextInt(10);
            }
        }
        int[][] answerMatrixA = new int[20][20];

        System.out.println("Non threaded method: ");
        SolveThread(matrixA, matrixB, 0, 20, answerMatrixA);
        for(int i = 0; i < matrixB.length; i++) {
            System.out.println(Arrays.toString(answerMatrixA[i]));
        }

        int[][] answerMatrix = new int[20][20];
        System.out.println("\nThreaded method: ");
        SolveThreadClass[] threads = new SolveThreadClass[5];
        for(int i = 0; i < 5; i++) {
            threads[i] = new SolveThreadClass(matrixA, matrixB, i * 20/5, (i+1) * 20/5, answerMatrix);
            threads[i].start();
        }

        for(int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e){
                System.out.println(e.toString());
            }
        }
        for(int i = 0; i < matrixB.length; i++) {
            System.out.println(Arrays.toString(answerMatrix[i]));
        }
    }

    public static void SolveThread(int[][] matrixA, int[][] matrixB, int firstRow, int lastRow, int[][] answerMatrix){
        for(int i = firstRow; i < lastRow; i++){
            for(int j = 0; j < matrixB[i].length; j++) {
                int currElement = 0;
                for(int k = 0; k < matrixB.length; k++){
                    currElement += matrixA[i][k] * matrixB[k][j];
                }
                answerMatrix[i][j] = currElement;
            }
        }
    }
}

class SolveThreadClass extends Thread {
    int[][] matrixA;
    int[][] matrixB;
    int[][] answerMatrix;

    int firstRow;
    int lastRow;

    SolveThreadClass(int[][] matrixA, int[][] matrixB, int firstRow, int lastRow, int[][] answerMatrix){
        this.matrixA = matrixA.clone();
        this.matrixB = matrixB.clone();
        this.answerMatrix = answerMatrix.clone();
        this.firstRow = firstRow;
        this.lastRow = lastRow;
    }

    public void start(){
        for(int i = firstRow; i < lastRow; i++){
            for(int j = 0; j < matrixB[i].length; j++) {
                int currElement = 0;
                for(int k = 0; k < matrixB.length; k++){
                    currElement += matrixA[i][k] * matrixB[k][j];
                }
                answerMatrix[i][j] = currElement;
            }
        }
    }
}