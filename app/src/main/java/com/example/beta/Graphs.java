package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.beta.FBref.mAuth;
import static com.example.beta.FBref.refEX;
import static com.example.beta.FBref.refINC;

/**
 * The type Graphs.
 */
public class Graphs extends AppCompatActivity {

    ArrayList<Integer> monthlist = new ArrayList<Integer>();
    ArrayList<Integer> pricelist = new ArrayList<Integer>();
    ArrayList<Integer> monthlist2 = new ArrayList<Integer>();
    ArrayList<Integer> pricelist2 = new ArrayList<Integer>();
    String uid = " ";
    int str3, str4;
    int sum, sum2, sum3, sum4, sum5, sum6;
    Spinner spinner;
    String[] spin = {"Expenses", "Incomes"};
    GraphView graph, graph2;
    Calendar cal = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    BarGraphSeries<DataPoint> series, series2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        spinner = (Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spin);
        spinner.setAdapter(adp);

        graph = (GraphView) findViewById(R.id.graph);
        graph2 = (GraphView) findViewById(R.id.graph2);

        /**
         * שם במשתנים את שמות החודשים בחצי השנה האחרונה
         */
        cal.add(Calendar.MONTH, 0);
        String month0 = new SimpleDateFormat("MMM").format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String month1 = new SimpleDateFormat("MMM").format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String month2 = new SimpleDateFormat("MMM").format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String month3 = new SimpleDateFormat("MMM").format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String month4 = new SimpleDateFormat("MMM").format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String month5 = new SimpleDateFormat("MMM").format(cal.getTime());

        /**
         * כותב את החודשים בציר האופקי של הגרף
         */

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{month0, month1, month2, month3, month4, month5});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        StaticLabelsFormatter staticLabelsFormatter2 = new StaticLabelsFormatter(graph2);
        staticLabelsFormatter2.setHorizontalLabels(new String[]{month0, month1, month2, month3, month4, month5});
        graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);

    }

    /**
     * Graphh.
     *קורא מהfirebase בהתאם למה שנבחר ברשימה
     * @param view the view
     */
    public void graphh(View view) {


        if (spinner.getSelectedItemPosition() == 0) {

            Query query = refEX.child(uid).orderByChild("edate");
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    monthlist.clear();
                    pricelist.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        expensesC expences = data.getValue(expensesC.class);

                        str3 = expences.getEprice();
                        str4 = expences.getEmonth();
                        monthlist.add(str4);
                        pricelist.add(str3);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            graph.setTitle("Expenses");

            /**
             * שם במשתנים את מספרי החודשים בחצי שנה האחרונה
             */

            int month00 = cal2.get(Calendar.MONTH);
            int month11 = (((month00 - 1) + 12) % 12);
            int month22 = (((month00 - 2) + 12) % 12);
            int month33 = (((month00 - 3) + 12) % 12);
            int month44 = (((month00 - 4) + 12) % 12);
            int month55 = (((month00 - 5) + 12) % 12);

            if (month11 == 0) {
                month11 = month11 + 12;
            }
            if (month22 == 0) {
                month22 = month22 + 12;
            }
            if (month33 == 0) {
                month33 = month33 + 12;
            }
            if (month44 == 0) {
                month44 = month44 + 12;
            }
            if (month55 == 0) {
                month55 = month55 + 12;
            }

            /**
             * סוכם את סכומי ההוצאות/הכנסות לפי מספרי החודשים בחצי שנה האחרונה
             */

            for (int i = 0; i < monthlist.size(); i++) {
                if (monthlist.get(i) == (month00 + 1)) {
                    sum = sum + pricelist.get(i);
                }
                if (monthlist.get(i) == (month11 + 1)) {
                    sum2 = sum2 + pricelist.get(i);
                }
                if (monthlist.get(i) == (month22 + 1)) {
                    sum3 = sum3 + pricelist.get(i);
                }
                if (monthlist.get(i) == (month33 + 1)) {
                    sum4 = sum4 + pricelist.get(i);
                }
                if (monthlist.get(i) == (month44 + 1)) {
                    sum5 = sum5 + pricelist.get(i);
                }
                if (monthlist.get(i) == (month55 + 1)) {
                    sum6 = sum6 + pricelist.get(i);
                }
            }
            /**
             * מציג את הנתונים על הגרף
             */
            try {
                series = new BarGraphSeries<>(new DataPoint[]{

                        new DataPoint(0, sum),
                        new DataPoint(2, sum2),
                        new DataPoint(4, sum3),
                        new DataPoint(6, sum4),
                        new DataPoint(8, sum5),
                        new DataPoint(10, sum6)

                });
                graph.addSeries(series);
                series.setDrawValuesOnTop(true);
                series.setSpacing(20);
            } catch (IllegalArgumentException e) {
                Toast.makeText(Graphs.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
        if (spinner.getSelectedItemPosition() == 1) {

            Query query = refINC.child(uid).orderByChild("idate");
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dS) {
                    monthlist.clear();
                    pricelist.clear();

                    for (DataSnapshot data : dS.getChildren()) {
                        IncomesC inc2 = data.getValue(IncomesC.class);

                        str3 = inc2.getIprice();
                        str4 = inc2.getImonth();
                        monthlist2.add(str4);
                        pricelist2.add(str3);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            graph2.setTitle("Incomes");
            /**
             * שם במשתנים את מספרי החודשים בחצי שנה האחרונה
             */

            int month00 = cal2.get(Calendar.MONTH);
            int month11 = (((month00 - 1) + 12) % 12);
            int month22 = (((month00 - 2) + 12) % 12);
            int month33 = (((month00 - 3) + 12) % 12);
            int month44 = (((month00 - 4) + 12) % 12);
            int month55 = (((month00 - 5) + 12) % 12);

            if (month11 == 0) {
                month11 = month11 + 12;
            }
            if (month22 == 0) {
                month22 = month22 + 12;
            }
            if (month33 == 0) {
                month33 = month33 + 12;
            }
            if (month44 == 0) {
                month44 = month44 + 12;
            }
            if (month55 == 0) {
                month55 = month55 + 12;
            }

            /**
             * סוכם את סכומי ההוצאות/הכנסות לפי מספרי החודשים בחצי שנה האחרונה
             */

            for (int i = 0; i < monthlist2.size(); i++) {
                if (monthlist2.get(i) == (month00 + 1)) {
                    sum = sum + pricelist2.get(i);
                }
                if (monthlist2.get(i) == (month11 + 1)) {
                    sum2 = sum2 + pricelist2.get(i);
                }
                if (monthlist2.get(i) == (month22 + 1)) {
                    sum3 = sum3 + pricelist2.get(i);
                }
                if (monthlist2.get(i) == (month33 + 1)) {
                    sum4 = sum4 + pricelist2.get(i);
                }
                if (monthlist2.get(i) == (month44 + 1)) {
                    sum5 = sum5 + pricelist2.get(i);
                }
                if (monthlist2.get(i) == (month55 + 1)) {
                    sum6 = sum6 + pricelist2.get(i);
                }
            }
            /**
             * מציג את הנתונים על הגרף
             */
            try {
                series2 = new BarGraphSeries<>(new DataPoint[]{

                        new DataPoint(0, sum),
                        new DataPoint(2, sum2),
                        new DataPoint(4, sum3),
                        new DataPoint(6, sum4),
                        new DataPoint(8, sum5),
                        new DataPoint(10, sum6)

                });
                graph2.addSeries(series2);
                series2.setDrawValuesOnTop(true);
                series2.setSpacing(20);
            } catch (IllegalArgumentException e) {
                Toast.makeText(Graphs.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * הפעולה יוצרת תפריט ןכאשר כפתור בתפריט נלחץ המסך עובר למסך אחר בהתאמה לשם של הכפתור
     * @param menu
     * @return
     */

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("Personal Area")) {
            Intent si = new Intent(this, PersonalArea.class);
            startActivity(si);
        }
        if (st.equals("Expenses")) {
            Intent si = new Intent(this, Expenses.class);
            startActivity(si);
        }
        if (st.equals("Incomes")) {
            Intent si = new Intent(this, Incomes.class);
            startActivity(si);
        }
        if (st.equals("Graphs")) {
            Intent si = new Intent(this, Graphs.class);
            startActivity(si);
        }
        if (st.equals("Credits")){
            Intent si = new Intent(this, Credits.class);
            startActivity(si);
        }

        return true;

    }


}





