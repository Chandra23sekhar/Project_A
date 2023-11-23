package com.example.project_a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DatabaseManipulation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_manipulation);


        DBHelper dbHelper = new DBHelper(this); // db handler

        Button del_db = findViewById(R.id.deleteDatabse);
        EditText table_name = findViewById(R.id.table_name_view);
        Button show_content = findViewById(R.id.display_table_content);
        TextView table_content = findViewById(R.id.table_content);

        del_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteDatabase();
                Toast.makeText(DatabaseManipulation.this, "Deleted full database!", Toast.LENGTH_SHORT).show();
            }
        });


        show_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tbName = table_name.getText().toString();

                String res = dbHelper.showSpecificTable(tbName);
                table_content.setText(res);
            }
        });
    }
}