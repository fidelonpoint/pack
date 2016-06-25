package gdg.ng.pack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

import gdg.ng.pack.MyReportFeed.MyReportAdapter;
import gdg.ng.pack.MyReportFeed.MyReportItem;

public class MyReport extends AppCompatActivity {
    GridView gridview;
    MyReportAdapter gridviewAdapter;
    ArrayList<MyReportItem> data = new ArrayList<MyReportItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // getting a reference to the gridview
        gridview = (GridView) findViewById(R.id.gridView3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        data.add(new MyReportItem("httpp","Na wa for this kind Ministry of health","#13 Ikpa road","12hrs ago", "23 Support","2,000",R.drawable.dirt));
        data.add(new MyReportItem("httpp","Na wa for this kind Ministry of health","#13 Ikpa road","12hrs ago", "23 Support","2,000",R.drawable.dirt));

   setDataAdapter();
    }
    // this method binds the dummy data to the adapter
    private void setDataAdapter() {
        gridviewAdapter = new MyReportAdapter(this, R.layout.myreport_list, data);
        gridview.setAdapter(gridviewAdapter);
    }


}
