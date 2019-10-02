package com.jvanwykdev.todo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        //return super.onPreferenceTreeClick(preference);

        String key = preference.getKey();
        if (key.equals("help")){


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Help");
            builder.setMessage("To Do tab:\n" +
                    "\n"+
                    "Single press to edit\n" +
                    "Long press to mark as done.\n" +
                    "\n"+
                    "Done tab:\n" +
                    "\n"+
                    "Single press to move back\n" +
                    "Long press to delete.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {

                    //action on dialog close
                }

            });

            builder.show();

            return true;
        }
        return false;
    }
}