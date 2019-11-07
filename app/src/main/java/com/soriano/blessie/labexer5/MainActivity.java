package com.soriano.blessie.labexer5;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] names, version, api, date, message;
    ListView listView;
    ArrayList<AndroidVersions> androidVersions = new ArrayList<>();

    int[] logos = {R.drawable.cupcake, R.drawable.donut, R.drawable.eclair, R.drawable.froyo, R.drawable.gingerbread,
            R.drawable.honeycomb, R.drawable.icecream, R.drawable.jellybean, R.drawable.kitkat, R.drawable.lollipop,
            R.drawable.marshmallow, R.drawable.nougat, R.drawable.oreo, R.drawable.pie, R.drawable.ten};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names = getResources().getStringArray(R.array.android);
        version = getResources().getStringArray(R.array.versions);
        api = getResources().getStringArray(R.array.apilevel);
        date = getResources().getStringArray(R.array.released);
        message = getResources().getStringArray(R.array.messages);

        for (int i = 0; i < names.length; i++) {
            androidVersions.add(new AndroidVersions(logos[i], names[i], version[i], api[i], date[i], message[i]));
        }
        listView = findViewById(R.id.listView);
        AndroidVAdapter adapter = new AndroidVAdapter(this, R.layout.item, androidVersions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int i, long id) {

        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(folder, "college.txt");
        final File file1 = new File(folder, "college1.txt");
        try {
            final FileOutputStream fos = new FileOutputStream(file);
            final FileOutputStream fos1 = new FileOutputStream(file1);
            String choice = "Android Version Name: " + androidVersions.get(i).getName() + "\n" +
                    "Android Version: " + androidVersions.get(i).getVersion() + "\n" +
                    "API Level: " + androidVersions.get(i).getApiLevel() + "\n" +
                    "Release Date: " + androidVersions.get(i).getReleaseDate();
            String choice1 = "Android Version Name: " + androidVersions.get(i).getName() + "\n" +
                    "Release Date: " + androidVersions.get(i).getReleaseDate();

            fos.write(choice.getBytes());
            fos1.write(choice1.getBytes());
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(androidVersions.get(i).getName());
            dialog.setIcon(androidVersions.get(i).getLogo());
            dialog.setMessage(message[i]);
            dialog.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    try {
                        FileInputStream input = new FileInputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/college1.txt"));
                        int i;
                        String string = " ";
                        while ((i = input.read()) != -1) {
                            string += Character.toString((char) i);
                        }
                        input.close();
                        Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            dialog.create().show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}