package com.example.shrad.shopsqlitedb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editfirstName, editLastName, editEmailId, editMobileNo, editId;
    Button btnaddData;
    Button btnviewAll;
    Button btnUpdateData;
    Button btnDeleteData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editfirstName = (EditText) findViewById(R.id.firstNameTxt);
        editLastName = (EditText) findViewById(R.id.lastNameTxt);
        editEmailId = (EditText) findViewById(R.id.emailIdTxt);
        editMobileNo = (EditText) findViewById(R.id.mobileNumberTxt);
        editId = (EditText) findViewById(R.id.idTxt);

        btnaddData = (Button) findViewById(R.id.btnAddData);
        btnviewAll =(Button) findViewById(R.id.btnViewAllData);
        btnUpdateData= (Button) findViewById(R.id.btnUpdateData);
        btnDeleteData = (Button) findViewById(R.id.btnDeleteData);

        addData();
        viewAll();
        deleteData();
        updateData();

    }


    private void updateData() {
        btnUpdateData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDb.updataData(editId.getText().toString(),
                                 editfirstName.getText().toString(),editLastName.getText().toString(),
                                 editMobileNo.getText().toString(),editEmailId.getText().toString());
                         if(isUpdate==true){
                             Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                         } else {
                             Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                         }
 
                     }
                 }
         );
     }
 
     private void deleteData() {
         btnDeleteData.setOnClickListener(
                 new View.OnClickListener(){
                     @Override
                     public void onClick(View view) {
                         Integer deletedRows = myDb.deleteData(editId.getText().toString());
                         if(deletedRows>0)
                             Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                         else
                             Toast.makeText(MainActivity.this,"Data Not Deleted",Toast.LENGTH_LONG).show();
                     }
                 }
         );
     }
 
     public void addData(){
         btnaddData.setOnClickListener(
                 new View.OnClickListener(){
                     @Override
                     public void onClick(View view) {
                        boolean isInserted =  myDb.insertData(editfirstName.getText().toString(),
                                editLastName.getText().toString(),editMobileNo.getText().toString(),editEmailId.getText().toString());
                         if(isInserted==true) {
                             Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                         } else {
                             Toast.makeText(MainActivity.this, "Data Not inserted", Toast.LENGTH_LONG).show();
                         }
                         openNewActivity();
                     }
                 }
         );
 
     }
 
     public void openNewActivity(){
         Intent intent = new Intent(this,Main2Activity.class);
         startActivity(intent);
     }

     public void viewAll() {
         btnviewAll.setOnClickListener(
                 new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Cursor res = myDb.getAllData();
                         if (!(myDb.foundData())){
                             Toast.makeText(MainActivity.this,"No Data found",Toast.LENGTH_LONG).show();
                         }
                         else{
                             if(res.getCount() == 0) {
                             showMessage("Error","Nothing found");
                             return;
                         }
 
                         StringBuffer buffer = new StringBuffer();
                         while (res.moveToNext()) {
                             buffer.append("Id :"+ res.getString(0)+"\n");
                             buffer.append("First Name :"+ res.getString(1)+"\n");
                             buffer.append("Last Name:"+ res.getString(2)+"\n");
                             buffer.append("Mobile No :"+ res.getString(3)+"\n");
                             buffer.append("Email ID:"+ res.getString(4)+"\n\n");
                         }

                         showMessage("Data",buffer.toString());
                     }
                     }
                 }
         );
     }
     public void showMessage(String title, String message){
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setCancelable(true);
         builder.setTitle(title);
         builder.setMessage(message);
         builder.show();
 
     }

    }
