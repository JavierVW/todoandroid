package com.jvanwykdev.todo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class AddDialog extends AppCompatDialogFragment {

    private EditText addNewAct;
    public AddDialogListener listener;

    ToDo todoAdd = new ToDo();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.layout_dialog,null);
        View view = inflater.inflate(R.layout.layout_dialog,null);
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        addNewAct = view.findViewById(R.id.addTask);
        builder.setView(view)
                .setTitle("Add task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String adding = addNewAct.getText().toString();
                        if(adding.length() != 0) {
                            listener.applyTexts(adding);


                        }
                        else{
                            Toast.makeText(getContext(), "Invalid input: Value empty!",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        return builder.create();
    }

    public interface AddDialogListener{
        void applyTexts(String addingStr);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddDialogListener");
        }
    }
//

}
