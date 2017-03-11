import com.sun.corba.se.impl.oa.toa.TOA;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String FILE_NAME = "H:\\ideaProj\\transportProblem\\input.txt";

    public static void main(String[] args) throws IOException {
        //Files.lines(Paths.get(FILE_NAME), StandardCharsets.UTF_8).forEach(System.out::println);
        //Files.lines(Paths.get(FILE_NAME), StandardCharsets.UTF_8).toArray(new String[lines.size()]);
        //List<String> stringList = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
        //String[] stringArray = stringList.toArray(new String[]{});
        //System.out.println(strArrChecker(stringArray));
        TProblem tProblem = new TProblem(FILE_NAME);
        if (tProblem!=null)
        tProblem.makePlanSZ();

    }

    private static int strArrToInt(String[] string) {
        //  for (int i = 0, j = 0; )
        return 0;
    }


}
