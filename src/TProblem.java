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
    int[][] costArray;
    int[][] plan;
    int[] need;
    int[] have;
    int min, imin, jmin;
    int sizeI;
    int sizeJ;

    /*   public void setStringArray(String FILE_NAME) throws IOException {
           List<String> stringList = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
           stringArray = stringList.toArray(new String[]{});
       }
   */

    public void makePlanMin() {
        printMass();
        while (!needEqHave()) {
            findMin();
            processMin();
        }
        System.out.println("plan:");
        printMass(plan);
    }

    public void makePlanSZ() {
        printMass();
        processSZ();
        System.out.println("plan:");
        printMass(plan);
    }


    public TProblem(String FILE_NAME) throws IOException {
        List<String> stringList = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
        stringArray = stringList.toArray(new String[]{});
        if (strArrChecker()) {
            costArray = convertToIntArr(stringArray);
            if (closeChecker()) {
                plan = makePlan(costArray);
                need = makeNeed(costArray);   //converting double array to 2 sigle
                have = makeHave(costArray);
                System.out.println("Import success");
                System.out.println("" + "I: " + sizeI + " J: " + sizeJ);
            } else System.out.println("Import failed. problem not close");
        }
    }

    public void findMin() {
        min = 0;
        for (int i = 0; i < sizeI && min == 0; i++) {
            for (int j = 0; j < sizeJ && min == 0; j++) {
                if (costArray[i][j] != 0)
                    min = costArray[i][j];
                imin = i;
                jmin = j;
            }
        }
        //min = costArray[0][0];
        for (int i = 0; i < sizeI; i++) {
            for (int j = 0; j < sizeJ; j++) {
                if (min > costArray[i][j] && costArray[i][j] > 0) {
                    min = costArray[i][j];
                    imin = i;
                    jmin = j;
                }
            }
        }
    }

    private void processSZ() {
        for (int i = 0; i < sizeI; i++) {
            for (int j = 0; j < sizeJ && costArray[i][sizeJ] > 0; j++) {
                //if need>have
                if (costArray[sizeI][j] > costArray[i][sizeJ]) {
                    plan[i][j] = costArray[i][sizeJ];
                    costArray[sizeI][j] = costArray[sizeI][j] - costArray[i][sizeJ];
                    costArray[i][sizeJ] = 0;
                }
                //if need<have
                if (costArray[sizeI][j] < costArray[i][sizeJ]) {
                    plan[i][j] = costArray[sizeI][j];
                    costArray[i][sizeJ] = costArray[i][sizeJ] - costArray[sizeI][j];
                    costArray[sizeI][j] = 0;
                }
                //if need=have
                if (costArray[sizeI][j] == costArray[i][sizeJ]) {
                    plan[i][j] = costArray[i][sizeJ];
                    costArray[i][sizeJ] = 0;
                    costArray[sizeI][j] = 0;
                }
            }
        }
    }

    public void processMin() {
        //If need>have
        if (costArray[sizeI][jmin] > costArray[imin][sizeJ]) {
            plan[imin][jmin] = costArray[imin][sizeJ];
            costArray[sizeI][jmin] = costArray[sizeI][jmin] - costArray[imin][sizeJ];
            costArray[imin][sizeJ] = 0;
            for (int j = 0; j < sizeJ; j++) {
                costArray[imin][j] = 0;
            }
        } else
            //If need<have
            if (costArray[sizeI][jmin] < costArray[imin][sizeJ]) {
                plan[imin][jmin] = costArray[sizeI][jmin];
                costArray[imin][sizeJ] = costArray[imin][sizeJ] - costArray[sizeI][jmin];
                costArray[sizeI][jmin] = 0;
                for (int i = 0; i < sizeI; i++) {
                    costArray[i][jmin] = 0;
                }
            } else
                //If need=have
                if (costArray[sizeI][jmin] == costArray[imin][sizeJ]) {
                    plan[imin][jmin] = costArray[sizeI][jmin];
                    for (int i = 0; i <= sizeI; i++) {
                        costArray[i][jmin] = 0;
                    }
                    for (int j = 0; j <= sizeJ; j++) {
                        costArray[imin][j] = 0;
                    }
                }
        costArray[imin][jmin] = 0;
        // costArray[imin][sizeJ] = costArray[imin][sizeJ] - costArray[imin][jmin];
        // costArray[sizeI][jmin] = costArray[sizeI][jmin] - costArray[imin][jmin];
        // costArray[imin][jmin] = 0;

    }

    private void printMass(int[][] mass) {
        for (int i = 0; i <= sizeI; i++) {
            for (int j = 0; j <= sizeJ; j++) {
                System.out.print(mass[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printMass() {
        for (int i = 0; i <= sizeI; i++) {
            for (int j = 0; j <= sizeJ; j++) {
                System.out.print(costArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    //check if all shops got resources from stock
    private boolean needEqHave() {
        int n = 0;
        for (int i = 0; i < sizeI && n == 0; i++)
            n += costArray[i][sizeJ];
        for (int j = 0; j < sizeJ && n == 0; j++)
            n += costArray[sizeI][j];
        return n == 0 ? true : false;
    }

    //Check if need == have
    private boolean closeChecker() {
        int need = 0, have = 0;
        for (int i = 0; i < sizeI; i++)
            have += costArray[i][sizeJ];
        for (int j = 0; j < sizeJ; j++)
            need += costArray[sizeI][j];
        return need == costArray[sizeI][sizeJ] && have == costArray[sizeI][sizeJ] ? true : false;
    }

    private int[][] convertToIntArr(String[] stringArray) {
        int[][] intArray = new int[sizeI + 1][sizeJ + 1];
        for (int i = 0; i <= sizeI; i++) {
            Scanner scanner = new Scanner(stringArray[i]);
            for (int j = 0; j <= sizeJ; j++) {
                intArray[i][j] = scanner.nextInt();
            }
        }
        return intArray;
    }

    private int[][] makePlan(int[][] arr) {
        int[][] planArray = new int[sizeI + 1][sizeJ + 1];
        for (int i = 0; i <= sizeI; i++)
            planArray[i][sizeJ] = arr[i][sizeJ];
        for (int j = 0; j <= sizeJ; j++)
            planArray[sizeI][j] = arr[sizeI][j];
        return planArray;
    }

    private int[] makeNeed(int[][] arr) {
        int[] need = new int[sizeJ];
        for (int j = 0; j < sizeJ; j++)
            need[j] = arr[sizeI][j];
        return need;
    }

    private int[] makeHave(int[][] arr) {
        int[] have = new int[sizeI];
        for (int i = 0; i < sizeI; i++)
            have[i] = arr[i][sizeJ];
        return have;
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
                setSizeJ(tmp - 1);
                setSizeI(stringArray.length - 1);
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
