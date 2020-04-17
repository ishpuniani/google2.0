package com.mycompany.app.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiscUtils {

    public static void plotPRGraph(String trecResultsPath, String imagePath) throws IOException {
        XYSeries prSeries = new XYSeries("PR");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(trecResultsPath)));
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.contains("iprec_at_recall")) {
                String prLine = line;
                String[] prArr = prLine.split("\t");
                double x = Double.parseDouble(prArr[0].trim().split("_")[3]);
                double y = Double.parseDouble(prArr[2].trim());
                prSeries.add(x, y);
            }
        }

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(prSeries);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "P/R Graph",
                "Recall",
                "Precision",
                dataset,
                PlotOrientation.VERTICAL,
                false, false, false);

        int width = 640;
        int height = 480;
        ChartUtilities.saveChartAsJPEG(new File(imagePath), xylineChart, width, height);
    }

    public static void main(String[] args) throws IOException {
        plotPRGraph("trec_results.txt", "pr-graph.jpeg");
    }
}
