package com.mycompany.app.Search;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    public static void write(List<Result> results, String resultsFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(resultsFile));

        for (Result res : results) {
            String str = String.format("%d\tITER\t%s\tRANK\t%f\tRUN\n", res.getQueryId(), res.getDocumentId(), res.getScore());
            writer.write(str);
        }
        writer.flush();
        writer.close();
    }
}
