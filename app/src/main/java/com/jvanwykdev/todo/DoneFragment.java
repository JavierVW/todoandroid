package com.jvanwykdev.todo;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneFragment extends Fragment {

    public DoneFragment() {
        // Required empty public constructor
    }



    DatabaseHelper todoDB;
    private ListView listView;

    ArrayList<String> doneItems = new ArrayList<String>();

    ArrayAdapter<String> listViewAdapter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            doneItems.clear();
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        todoDB = new DatabaseHelper(getContext());


        //inflate the layout first
        View view = inflater.inflate(R.layout.fragment_done, container, false);


        listView = (ListView) view.findViewById(R.id.doneMenu);

        final Cursor data = todoDB.getListContentsDone();

        populateList();
        return view;
    }




    public void populateList() {
        Cursor data = todoDB.getListContentsDone();
        ArrayList<String> listData = new ArrayList<>();
        listView.setLongClickable(true);

        while (data.moveToNext()) {
            doneItems.add(data.getString(1));
            listViewAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, doneItems);
            listView.setAdapter(listViewAdapter);
            listViewAdapter.notifyDataSetChanged();


        }



        //-------------NEW PART ----------
        //Long press will delete, short press will move
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();

                Log.i("onclick"," onClick LONG Reached!");
                Cursor cData = todoDB.getItemIDDone(name);



                int itemID = -1;
                while (cData.moveToNext()){
                    itemID = cData.getInt(0);

                }

                if (itemID > -1){
                    todoDB.deleteDataDone(itemID,name);
                    listViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"Task permanently deleted!",Toast.LENGTH_LONG).show();
                    doneItems.clear();
                    listViewAdapter.notifyDataSetChanged();
                    populateList();
                }
                else
                    Toast.makeText(getContext(),"No ID found!",Toast.LENGTH_LONG).show();

                return true;
            }
        });


        //--------------EDIT--------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = adapterView.getItemAtPosition(i).toString();

                Log.i("onclick"," onClick Reached!");
                Cursor cData = todoDB.getItemIDDone(name);



                int itemID2 = -1;
                while (cData.moveToNext()){
                    itemID2 = cData.getInt(0);

                }

                if (itemID2 > -1){
//setup the swipe to done method and use the done table's methods here. it currently messes with the original list
                    todoDB.movedBack(itemID2,name);
                    listViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"Task moved to TODO!",Toast.LENGTH_LONG).show();
                    doneItems.clear();
                    listViewAdapter.notifyDataSetChanged();
                    populateList();
                }
                else
                    Toast.makeText(getContext(),"No ID found!",Toast.LENGTH_LONG).show();
            }
        });



        //-------END----------
    }


}


//TODO refresh fragments on view