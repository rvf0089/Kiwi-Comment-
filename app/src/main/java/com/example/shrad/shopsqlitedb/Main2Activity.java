package com.example.shrad.shopsqlitedb;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The Main2Activity opens the Shop details form for the shop owner to be filled out.
 * The data entered in the textfield is saved in the database table ShopDetails.
 * The class also allows to update, delete & view the shopdetails table.
*/
public class Main2Activity extends AppCompatActivity {

    DatabaseHelper mydb;

    EditText editId,editShopName, editRegNo, editGSTNo, editPhoneno, editWebsiteLink;
    EditText editStreetAddress, editSuburb, editCity, editCountry, editPostcode;

    Button btnaddShopData;
    Button btnviewAllShopData;
    Button btnUpdateShopData;
    Button btnDeleteShopData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mydb = new DatabaseHelper(this);

        editId =(EditText) findViewById(R.id.idTxt);
        editShopName =(EditText) findViewById(R.id.shopNameTxt);
        editRegNo =(EditText) findViewById(R.id.regnoTxt);
        editGSTNo =(EditText) findViewById(R.id.gstNumberTxt);
        editPhoneno =(EditText) findViewById(R.id.shopPhoneNumberTxt);
        editWebsiteLink =(EditText) findViewById(R.id.shopwebsiteaddressTxt);
        editStreetAddress =(EditText) findViewById(R.id.streetAddress1Txt);
        editSuburb =(EditText) findViewById(R.id.suburbTxt);
        editCity =(EditText) findViewById(R.id.CityTxt);
        editCountry =(EditText) findViewById(R.id.countryTxt);
        editPostcode =(EditText) findViewById(R.id.postcodeTxt);

        btnaddShopData = (Button) findViewById(R.id.btnAddData);
        btnviewAllShopData =(Button) findViewById(R.id.btnViewAllData);
        btnUpdateShopData= (Button) findViewById(R.id.btnUpdateData);
        btnDeleteShopData = (Button) findViewById(R.id.btnDeleteData);

        addData();
        viewAll();
        deleteData();
        updateData();
    }

    /**
    *The addData() method is called when Add Shop Data button is pressed by the user. the listener onClick method is implemented
    *in this method. If the data insert is successful, then a toast is represented saying data inserted else data not inserted.
    */
    public void addData(){
        btnaddShopData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        boolean isInserted =  mydb.insertShopData(editShopName.getText().toString(),
                                editRegNo.getText().toString(),editGSTNo.getText().toString(),editPhoneno.getText().toString(),
                                editWebsiteLink.getText().toString(), editStreetAddress.getText().toString(),
                                editSuburb.getText().toString(),editCity.getText().toString(),editCountry.getText().toString(),
                                editPostcode.getText().toString());
                        if(isInserted==true) {
                            Toast.makeText(Main2Activity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Main2Activity.this, "Data Not inserted", Toast.LENGTH_LONG).show();
                        }
                        openLocationIntent();
                    }
                }
        );

    }

     /**
    *The updateData() method is called when Update Shop Data button is pressed by the user. the listener onClick method is implemented
    *in this method & to identify the record to be updated the user needs to enter their id then only the update operation could be done.
    *If the data updated is successful, then a toast is represented saying data updated else data not updated.
    */
    private void updateData() {
        btnUpdateShopData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = mydb.updataShopData(editId.getText().toString(),editShopName.getText().toString(),
                                editRegNo.getText().toString(),editGSTNo.getText().toString(),editPhoneno.getText().toString(),
                                editWebsiteLink.getText().toString(), editStreetAddress.getText().toString(),
                                editSuburb.getText().toString(),editCity.getText().toString(),editCountry.getText().toString(),
                                editPostcode.getText().toString());
                        if(isUpdate==true){
                            Toast.makeText(Main2Activity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Main2Activity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

     /**
    *The deleteData() method is called when Deleted Shop Data button is pressed by the user. the listener onClick method is implemented
    *in this method. to identify the record to be deleted the user needs to enter their id then only the delete operation could be done.
    *If the data delete is successful, then a toast is represented saying data deleted else data not deleted.
    */
    private void deleteData() {
        btnDeleteShopData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = mydb.deleteShopData(editId.getText().toString());
                        if(deletedRows>0)
                            Toast.makeText(Main2Activity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Main2Activity.this,"Data Not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

     /**
    *The viewAll() method is called when ViewAll Shop Data button is pressed by the user. the listener onClick method is implemented
    *in this method. If data is present is not present in the database, a toast is represented saying No data found else,
    * a alert dialog is shown up with all table data in it.
    */
    public void viewAll() {
        btnviewAllShopData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = mydb.getAllShopData();
                        if (!(mydb.foundData())){
                            Toast.makeText(Main2Activity.this,"No Data found",Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(res.getCount() == 0) {
                                showMessage("Error","Nothing found");
                                return;
                            }

                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append("Id :"+ res.getString(0)+"\n");
                                buffer.append("Shop Name :"+ res.getString(1)+"\n");
                                buffer.append("Shop Reg No:"+ res.getString(2)+"\n");
                                buffer.append("Shop GST No :"+ res.getString(3)+"\n");
                                buffer.append("Shop PhoneNo:"+ res.getString(4)+"\n");
                                buffer.append("Shop WebsiteLink :"+ res.getString(5)+"\n");
                                buffer.append("Shop Street Address:"+ res.getString(6)+"\n");
                                buffer.append("Shop Suburb :"+ res.getString(7)+"\n");
                                buffer.append("Shop City:"+ res.getString(8)+"\n\n");
                                buffer.append("Shop Country :"+ res.getString(9)+"\n");
                                buffer.append("Shop Post code:"+ res.getString(10)+"\n\n");

                            }

                            showMessage("Data",buffer.toString());
                        }
                    }
                }
        );
    }
    
    /**
    * This method shows alert dialog with all shop detail data when view all data button is pressed.
    */
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    
    /**
    * this method is used to call Map location activity when addData/ViewAllData button is pressed.
    */
    public void openLocationIntent(){
        Intent intent = new Intent(this,MapLocation.class);
        startActivity(intent);
    }
}
