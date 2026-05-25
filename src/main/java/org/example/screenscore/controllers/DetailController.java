package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

import java.util.List;

public class DetailController {
    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    public void updateStats(List<ReviewClass> list){
        pieUpdate(list);
        barUpdate(list);
    }

    private void pieUpdate(List<ReviewClass> list){
        long movieCount = list.stream().filter(r -> r.getType() == Type.Movie).count();
        long seriesCount = list.stream().filter(r -> r.getType() == Type.Series).count();
        long animeCount = list.stream().filter(r -> r.getType() == Type.Anime).count();

        pieChart.getData().clear();

        pieChart.getData().add(new PieChart.Data("Movies", (double) movieCount));
        pieChart.getData().add(new PieChart.Data("Series", (double) seriesCount));
        pieChart.getData().add(new PieChart.Data("Anime", (double) animeCount));
    }

    private void barUpdate(List<ReviewClass> list){
        int[] counts = new int[11];
        list.forEach(r -> counts[r.getRating()]++);

        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(int i = 0; i < counts.length; i++){
            if(counts[i] != 0){
                series.getData().add(new XYChart.Data<>(String.valueOf(i), counts[i]));
            }
        }

        barChart.getData().add(series);
    }
}
