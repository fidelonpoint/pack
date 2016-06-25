package gdg.ng.pack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import gdg.ng.pack.state.State_Adapter;
import gdg.ng.pack.state.Stateitem;

public class Manage_caterories extends AppCompatActivity {
    ListView listview;
    State_Adapter listAdapter;
    ArrayList<Stateitem> data = new ArrayList<Stateitem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_caterories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview = (ListView) findViewById(R.id.list_state);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data.add(new Stateitem("Abia"));
        data.add(new Stateitem("Adamawa"));
        data.add(new Stateitem("Akwa Ibom"));
        data.add(new Stateitem("Anambra"));
        data.add(new Stateitem("Bauchi"));
        data.add(new Stateitem("Bayelsa"));
        data.add(new Stateitem("Benue"));
        data.add(new Stateitem("Borno"));

        setDataAdapter();

    }
    // this method binds the dummy data to the adapter
    private void setDataAdapter() {
        listAdapter = new State_Adapter(Manage_caterories.this, R.layout.state_list_row, data);
        listview.setAdapter(listAdapter);
    }

}
