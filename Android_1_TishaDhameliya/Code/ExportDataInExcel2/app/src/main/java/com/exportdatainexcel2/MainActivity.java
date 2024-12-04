package com.exportdatainexcel2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button exportButton;
    List<Customer> customerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        exportButton=findViewById(R.id.exportButton);

        customerList = new ArrayList<>();

        //Add same data Manually
        customerList.add(new Customer("Harsh","h123@gmail.com","5806034245","Jamnagar",23));
        customerList.add(new Customer("Rakesh","rakesh@gmail.com","27584394","Bharuch",29));
        customerList.add(new Customer("Jensi","jns22@gmail.com","6943336570","Surat",25));

        CustomerAdapter adapter = new CustomerAdapter(customerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                else {
                    exportDataToExcel();
                }
            }
        });

    }

    private void exportDataToExcel(){
//        HSSFWorkbook workbook=new HSSFWorkbook();
//        HSSFSheet sheet=workbook.createSheet("Customer");
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("Customer");

        int rowIndex=0;

        Row header=sheet.createRow(rowIndex++);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("Phone");
        header.createCell(3).setCellValue("Address");
        header.createCell(4).setCellValue("Age");

        for(Customer customer:customerList){
            Row row= sheet.createRow(rowIndex++);
            header.createCell(0).setCellValue(customer.getName());
            header.createCell(1).setCellValue(customer.getEmail());
            header.createCell(2).setCellValue(customer.getPhone());
            header.createCell(3).setCellValue(customer.getAddress());
            header.createCell(4).setCellValue(customer.getAge());
        }


        try {
            File file=new File(Environment.getExternalStorageDirectory(),"CustomerData.xlsx");
            FileOutputStream outputStream=new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
            Toast.makeText(this, "Data Export to : "+ file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Export Failed", Toast.LENGTH_SHORT).show();
        }
f
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            exportDataToExcel();
        }
        else {
            Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
        }

    }
}