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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDo extends Fragment  implements AddDialog.AddDialogListener, EditDialog.EditDialogListener {

    private static final String TAG = "ToDoFragment";

    public ToDo() {
        // Required empty public constructor
    }


    DatabaseHelper todoDB;
    private ListView listView;


    public void AddInput(String newData){
        //newData = DatabaseUtils.sqlEscapeString(newData);
        //String newData = newData2.replace("'","''");
        boolean insertData = todoDB.addData(newData);

        if (insertData==true){
            //listViewAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Task added!",Toast.LENGTH_SHORT).show();
            //listViewAdapter.notifyDataSetChanged();

        }
        else {
            Toast.makeText(getContext(), "something went wrong",Toast.LENGTH_SHORT).show();
        }

    }


    ArrayList<String> menuItems = new ArrayList<String>();

    ArrayAdapter<String> listViewAdapter;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            menuItems.clear();
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();

            menuItems.clear();
            if (menuItems.size() != 0) {
                listViewAdapter.notifyDataSetChanged();
                //menuItems.clear();
                //populateList();
            }
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //setHasOptionsMenu(true);
        todoDB = new DatabaseHelper(getContext());


        //inflate the layout first
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);


        listView = (ListView) view.findViewById(R.id.mainMenu);

        final Cursor data = todoDB.getListContents();
        menuItems.clear();
        populateList();
        FloatingActionButton fab = view.findViewById(R.id.fab);





        //TODO
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //openDialog();
               //Log.d(TAG, "onClick: ");

               AddDialog addDialog = new AddDialog();
               addDialog.setTargetFragment(ToDo.this, 1);
               addDialog.show(getFragmentManager(),"add dialog");


               //listViewAdapter.notifyDataSetChanged();
           }
       });


        return view;

    }

    public void populateList() {
        Cursor data = todoDB.getListContents();
        ArrayList<String> listData = new ArrayList<>();
        listView.setLongClickable(true);

        while (data.moveToNext()) {
            menuItems.add(data.getString(1));
            listViewAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, menuItems);
            listView.setAdapter(listViewAdapter);
            listViewAdapter.notifyDataSetChanged();


        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EditDialog editD = new EditDialog();
                String name = adapterView.getItemAtPosition(i).toString();

                Log.i("onclick"," onClick Reached!");
                Cursor cData = todoDB.getItemID(name);



                int itemID = -1;
                while (cData.moveToNext()){
                    itemID = cData.getInt(0);

                }

                if (itemID > -1){

                    Bundle bundle = new Bundle();
                    bundle.putInt("id",itemID);
                    bundle.putString("name",name);
                    editD.setArguments(bundle);

                    editD.setTargetFragment(ToDo.this, 1);
                    editD.show(getFragmentManager(),"add dialog");
                    listViewAdapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(getContext(),"No ID found!",Toast.LENGTH_LONG).show();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                //name = name.replace("'","''");

                //name = DatabaseUtils.sqlEscapeString(name);
                Log.i("onclick"," onClick LONG Reached!");
                Cursor cData = todoDB.getItemID(name);



                int itemID = -1;
                while (cData.moveToNext()){
                    itemID = cData.getInt(0);

                }

                if (itemID > -1){
//TODO setup the swipe to done method and use the done table's methods here. it currently messes with the original list
                    todoDB.moveToDone(itemID,name);
                    listViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"Task moved to DONE!",Toast.LENGTH_LONG).show();
                    menuItems.clear();
                    listViewAdapter.notifyDataSetChanged();
                    populateList();
                }
                else
                    Toast.makeText(getContext(),"No ID found!",Toast.LENGTH_LONG).show();


                return true;
            }
        });
        //-------END----------
    }




    @Override
    public void applyTexts(String addingStr) {
        menuItems.clear();
        AddInput(addingStr);
        populateList();
        listViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void editTexts(String addingStr) {
        menuItems.clear();
        //AddInput(addingStr);
        populateList();
        listViewAdapter.notifyDataSetChanged();
    }


}


//TODO note I added replaceAll functions in some of the db