package com.example.itai.todolistmanagernew;

/**
 * Created by Itai on 22/04/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.itai.todolistmanagernew.DBHandler.TASK_COLUMN;

/**
 * Created by Itai on 22/03/2017.
 */

/*
public class ColorsListAdapter {
}
*/
public class ColorsListAdapter extends ArrayAdapter<JSONObject> {

    private Context mContext;
    private int id;
    private List<JSONObject> items ;

    public ColorsListAdapter(Context context, int textViewResourceId , List<JSONObject> list )
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }


    public ColorsListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.rowTextView);

        if(items.get(position) != null )
        {
            text.setTextColor(Color.WHITE);
            try {
                text.setText(items.get(position).getString(TASK_COLUMN));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(position % 2 == 0) {
                text.setBackgroundColor(Color.RED);
            } else {
                text.setBackgroundColor(Color.BLUE);
            }


        }

        return mView;
    }

}