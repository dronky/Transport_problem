import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sinky on 03.03.2017.
 */
public class TProblem {
    String[] stringArray;
    int[][] intArray;
    int min, imin, jmin;
    int sizeI;
    int sizeJ;

    /*   public void setStringArray(String FILE_NAME) throws IOException {
           List<String> stringList = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
           stringArray = stringList.toArray(new String[]{});
       }
   */

    public void main() {
        findMin();
        processMin();
        printMass();
    }

    public TProblem(String FILE_NAME) throws IOException {
        List<String> stringList = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
        stringArray = stringList.toArray(new String[]{});
        if (strArrChecker()) {
            System.out.println("Import success");
            System.out.println("" + "I: " + sizeI + " J: " + sizeJ);
        }
    }

    public void findMin() {
        intArray = convertToIntArr(stringArray);
        printMass(intArray);
        min = intArray[0][0];
        for (int i = 0; i < sizeI; i++) {
            for (int j = 0; j < sizeJ; j++) {
                if (min > intArray[i][j]) {
                    min = intArray[i][j];
                    imin = i;
                    jmin = j;
                }
            }
        }
    }

    public void processMin() {
        //If need>have
        if (intArray[sizeI][jmin] > intArray[imin][sizeJ]) {
            intArray[sizeI][jmin] = intArray[sizeI][jmin] - intArray[imin][sizeJ];
            for (int i=0;i<sizeI-1;i++){

            }

        }

        //If need<have
        //If need=have
       // intArray[imin][sizeJ] = intArray[imin][sizeJ] - intArray[imin][jmin];
       // intArray[sizeI][jmin] = intArray[sizeI][jmin] - intArray[imin][jmin];
       // intArray[imin][jmin] = 0;

    }

    private void printMass(int[][] mass) {
        for (int i = 0; i < sizeI; i++) {
            for (int j = 0; j < sizeJ; j++) {
                System.out.print(mass[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printMass() {
        for (int i = 0; i < sizeI; i++) {
            for (int j = 0; j < sizeJ; j++) {
                System.out.print(intArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int[][] convertToIntArr(String[] stringArray) {
        int[][] intArray = new int[sizeI][sizeJ];
        for (int i = 0; i < sizeI; i++) {
            Scanner scanner = new Scanner(stringArray[i]);
            for (int j = 0; j < sizeJ; j++) {
                intArray[i][j] = scanner.nextInt();
            }
        }
        return intArray;
    }


    private boolean strArrChecker() {
        boolean lampa;
        int tmp = 0, prevTmp = 0;
        //If Array filled
        if (stringArray.length != 0) {
            lampa = true;
        } else lampa = false;

        for (int n = 0; n < stringArray.length && lampa; n++) {
            Scanner scanner = new Scanner(stringArray[n]);
            try {
                while (scanner.hasNext()) {
                    tmp++;
                    scanner.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("input file broken");
                lampa = false;
            }
            if (n == 0) {
                prevTmp = tmp;
                setSizeJ(tmp-1);
                setSizeI(stringArray.length-1);
            } else if (!scanner.hasNext() && prevTmp != tmp) {
                lampa = false;
            }
            tmp = 0;
        }
        return lampa;
    }

    public void setSizeI(int sizeI) {
        this.sizeI = sizeI;
    }

    public void setSizeJ(int sizeJ) {
        this.sizeJ = sizeJ;
    }

}
