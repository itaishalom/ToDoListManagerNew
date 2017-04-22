package com.example.itai.todolistmanagernew;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ListView lv1;
    EditText inputSearch;
    ArrayList<String> toDos;
    ColorsListAdapter  adapter;
    int mCurrentDeleteToDo=0;

    int mYear;
    int mMount;
    int mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv1 = (ListView) findViewById(R.id.listView1);
        inputSearch = (EditText) findViewById(R.id.editText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toDos==null){
                    toDos= new ArrayList<String>();
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText memo = new EditText(MainActivity.this);
                DatePicker dpd = new DatePicker(getApplicationContext());
                dpd.init(2017,1,1,  new DatePicker.OnDateChangedListener() {

                    public void onDateChanged(DatePicker view, int yy, int mm, int dd) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(yy, mm, dd);
                        mYear=yy;
                        mMount = mm;
                        mDay = dd;
                    }
                });

                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(dpd);
                layout.addView(memo);
                alert.setView(layout);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        return;
                        // what ever you want to do with No option.
                    }
                });

                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String date = mDay +"."+mMount+"."+mYear;
                        toDos.add(date+" : "+memo.getText().toString());
                        adapter = new ColorsListAdapter(getApplicationContext(),  R.layout.simplerow, toDos);
                        lv1.setAdapter(adapter);
                    }
                });


                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //    ListView l = (ListView) arg0;
                //   l.
                mCurrentDeleteToDo =arg2;
                Object o = lv1.getItemAtPosition(arg2);
                final String memo = o.toString();
                Toast.makeText(getBaseContext(), o.toString(), Toast.LENGTH_SHORT).show();

                // Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Delete "+memo+" ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                toDos.remove(mCurrentDeleteToDo);
                                adapter = new ColorsListAdapter(getApplicationContext(),  R.layout.simplerow,toDos);
                                lv1.setAdapter(adapter);

                            }
                        });
                if (memo.toLowerCase().contains("call")) {
                    builder1.setNeutralButton("Call",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    //Assumes date : call number
                                    String[] tempArray = memo.split(" ");
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:"+tempArray[3]));
                                    startActivity(intent);

                                }
                            });
                }
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
