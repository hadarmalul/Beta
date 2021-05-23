package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Graphs extends AppCompatActivity {

    ArrayList<Integer> monthlist = new ArrayList<Integer>();
    ArrayList<Integer> pricelist = new ArrayList<Integer>();
    expensesC expences = new expensesC();
    int sum, sum2, sum3, sum4, sum5, sum6, sum7, sum8, sum9, sum10, sum11, sum12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        Intent gi = getIntent();
        monthlist = gi.getIntegerArrayListExtra("month");
        pricelist = gi.getIntegerArrayListExtra("price");



        final GraphView graph = (GraphView) findViewById(R.id.graph);
        Button button = findViewById(R.id.addButton);
        graph.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(int i=0; i<monthlist.size(); i++) {
                    switch (monthlist.get(i)){
                        case 1: sum = sum + pricelist.get(i);
                        break;
                        case 2: sum2 = sum2 + pricelist.get(i);
                        break;
                        case 3: sum3 = sum3 + pricelist.get(i);
                        break;
                        case 4: sum4 = sum4 + pricelist.get(i);
                        break;
                        case 5: sum5 = sum5 + pricelist.get(i);
                        break;
                        case 6: sum6 = sum6 + pricelist.get(i);
                        break;
                        case 7: sum7 = sum7 + pricelist.get(i);
                        break;
                        case 8: sum8 = sum8 + pricelist.get(i);
                        break;
                        case 9: sum9 = sum9 + pricelist.get(i);
                        break;
                        case 10: sum10 = sum10 + pricelist.get(i);
                        break;
                        case 11: sum11 = sum11 + pricelist.get(i);
                        break;
                        case 12: sum12 = sum12 + pricelist.get(i);
                        break;


                    }
                }

              /*  StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);**/
              try {
                    LineGraphSeries <DataPoint> series = new LineGraphSeries< >(new DataPoint[] {

                            new DataPoint(0, 0),
                            new DataPoint(1, sum),
                            new DataPoint(2, sum2),
                            new DataPoint(3, sum3),
                            new DataPoint(4, sum4),
                            new DataPoint(5, sum5),
                            new DataPoint(6, sum6),
                            new DataPoint(7, sum7),
                            new DataPoint(8, sum8),
                            new DataPoint(9, sum9),
                            new DataPoint(10, sum10),
                            new DataPoint(11, sum11),
                            new DataPoint(12, sum12)

                  });
                  graph.addSeries(series);
                } catch (IllegalArgumentException e) {
                  Toast.makeText(Graphs.this, e.getMessage(), Toast.LENGTH_LONG).show();
               }
            }
        });
    }
    }
