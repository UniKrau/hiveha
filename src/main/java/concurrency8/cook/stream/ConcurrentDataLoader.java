package concurrency8.cook.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lhao on 6/21/17.
 */
public class ConcurrentDataLoader {

    public static List<Record> load(Path path) throws IOException{

        List<String> lines = Files.readAllLines(path);

        List<Record> records = lines.parallelStream()
                                    .skip(1)
                                    .map( l-> l.split(";"))
                                    .map( t -> new Record(t))
                                    .collect(Collectors.toList());

        return records;
    }


    public static void main(String[] args) throws IOException{


        Path p1 = Paths.get("/Volumes/AgodaWork/Downloads/bank-additional/bank-additional.csv");

        ArrayList<Record> lists = (ArrayList<Record>) load(p1);

        for (Record r: lists)
            for (String s : r.getName()) {

                System.out.println(s);

            }

    }
}
