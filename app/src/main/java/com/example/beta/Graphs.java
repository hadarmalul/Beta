package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    int str1, str2, str3, str4;
    int sum, sum2, sum3, sum4, sum5, sum6, sum7, sum8, sum9, sum10, sum11, sum12;
    GraphView graph;
    Calendar cal = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    BarGraphSeries<DataPoint> series, series2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();



        graph = (GraphView) findViewById(R.id.graph);


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

    }

    /**
     * Graphh.
     * קורא מהfirebase בהתאם למה שנבחר ברשימה
     *
     * @param view the view
     */
    public void graphh(View view) {

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

        Query query2 = refINC.child(uid).orderByChild("idate");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dS) {
                monthlist2.clear();
                pricelist2.clear();

                for (DataSnapshot data : dS.getChildren()) {
                    IncomesC incomes = data.getValue(IncomesC.class);

                    str1 = incomes.getIprice();
                    str2 = incomes.getImonth();
                    monthlist2.add(str2);
                    pricelist2.add(str1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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
         * סוכם את סכומי ההוצאות/הכנסות לפי מספרי החודשים בחצי שנה האחרונה
         */

        for (int j = 0; j < monthlist2.size(); j++) {
            if (monthlist2.get(j) == (month00 + 1)) {
                sum7 = sum7 + pricelist2.get(j);
            }
            if (monthlist2.get(j) == (month11 + 1)) {
                sum8 = sum8 + pricelist2.get(j);
            }
            if (monthlist2.get(j) == (month22 + 1)) {
                sum9 = sum9 + pricelist2.get(j);
            }
            if (monthlist2.get(j) == (month33 + 1)) {
                sum10 = sum10 + pricelist2.get(j);
            }
            if (monthlist2.get(j) == (month44 + 1)) {
                sum11 = sum11 + pricelist2.get(j);
            }
            if (monthlist2.get(j) == (month55 + 1)) {
                sum12 = sum12 + pricelist2.get(j);
            }
        }
        /**
         * מציג את הנתונים על הגרף
         */

        int color = Color.RED;
        int color2 = Color.GREEN;
        series = new BarGraphSeries<>(new DataPoint[]{

                new DataPoint(0, sum),
                new DataPoint(2, sum2),
                new DataPoint(4, sum3),
                new DataPoint(6, sum4),
                new DataPoint(8, sum5),
                new DataPoint(10, sum6)

        });

        series.setColor(color);
        graph.addSeries(series);

        series2 = new BarGraphSeries<>(new DataPoint[]{

                new DataPoint(0, sum7),
                new DataPoint(2, sum8),
                new DataPoint(4, sum9),
                new DataPoint(6, sum10),
                new DataPoint(8, sum11),
                new DataPoint(10, sum12)

        });

        series2.setColor(color2);
        graph.addSeries(series2);
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
        if (st.equals("Statistics")) {
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





