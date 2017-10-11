package com.law.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks;
    Button btnAdd, btnViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editTextName);
        editSurname = (EditText) findViewById(R.id.editTextSurname);
        editMarks = (EditText) findViewById(R.id.editTextMarks);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnViewAll = (Button) findViewById(R.id.buttonShow);
        addData();
        viewAll();

    }

    public void addData(){
        btnAdd.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View v){
                   boolean isInserted =  myDb.insertData(editName.getText().toString(),
                            editSurname.getText().toString(),
                            editMarks.getText().toString());

                    if(isInserted = true)
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }


    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            //Show message
                            showMessage("Error", "No Data Faound");
                            return;
                        }else{
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()){
                                buffer.append("Id: " + res.getString(0) + "\n");
                                buffer.append("Name: " + res.getString(1) + "\n");
                                buffer.append("Surname: " + res.getString(2) + "\n");
                                buffer.append("Marks: " + res.getString(3) + "\n\n");
                            }

                            // Show all data
                            showMessage("Data", buffer.toString());
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
