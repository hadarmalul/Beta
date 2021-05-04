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

public class Graphs extends AppCompatActivity {

    EditText firstNum_1, secondNum_1;
    EditText firstNum_2, secondNum_2;
    EditText firstNum_3, secondNum_3;
    EditText firstNum_4, secondNum_4;
    String firstInput_1 = "0" , secondInput_1 = "0";
    String firstInput_2  = "0", secondInput_2 = "0";
    String firstInput_3 = "0", secondInput_3 = "0";
    String firstInput_4 = "0", secondInput_4 = "0";
    Expenses ex = new Expenses();
    expensesC expences = new expensesC();
    int sum, sum2, sum3, sum4, sum5, sum6, sum7, sum8, sum9, sum10, sum11, sum12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        firstNum_1 = findViewById(R.id.firstNum_1);
        secondNum_1 = findViewById(R.id.secondNum_1);
        firstNum_2 = findViewById(R.id.firstNum_2);
        secondNum_2 = findViewById(R.id.secondNum_2);
        firstNum_3 = findViewById(R.id.firstNum_3);
        secondNum_3 = findViewById(R.id.secondNum_3);
        firstNum_4 = findViewById(R.id.firstNum_4);
        secondNum_4 = findViewById(R.id.secondNum_4);

        Intent gi = new Intent();



        final GraphView graph = (GraphView) findViewById(R.id.graph);
        Button button = findViewById(R.id.addButton);
        graph.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  1 and 5
                firstInput_1 = firstNum_1.getText().toString();
                secondInput_1 = secondNum_1.getText().toString();
                firstInput_2 = firstNum_2.getText().toString();
                secondInput_2 = secondNum_2.getText().toString();
                firstInput_3 = firstNum_3.getText().toString();
                secondInput_3 = secondNum_3.getText().toString();
                firstInput_4 = firstNum_4.getText().toString();
                secondInput_4 = secondNum_4.getText().toString();

                for(int i=0; i<ex.exList4.size(); i++) {
                    switch (ex.exList4.get(i)){
                        case 1: sum = sum + ex.exList3.get(i);
                        break;
                        case 2: sum2 = sum2 + ex.exList3.get(i);
                        break;
                        case 3: sum3 = sum3 + ex.exList3.get(i);
                        break;
                        case 4: sum4 = sum4 + ex.exList3.get(i);
                        break;
                        case 5: sum5 = sum5 + ex.exList3.get(i);
                        break;
                        case 6: sum6 = sum6 + ex.exList3.get(i);
                        break;
                        case 7: sum7 = sum7 + ex.exList3.get(i);
                        break;
                        case 8: sum8 = sum8 + ex.exList3.get(i);
                        break;
                        case 9: sum9 = sum9 + ex.exList3.get(i);
                        break;
                        case 10: sum10 = sum10 + ex.exList3.get(i);
                        break;
                        case 11: sum11 = sum11 + ex.exList3.get(i);
                        break;
                        case 12: sum12 = sum12 + ex.exList3.get(i);
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
