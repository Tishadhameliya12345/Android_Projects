package com.finddatedifference;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtFromDate;
    TextView txtResult;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtFromDate=findViewById(R.id.edtFromDate);
        txtResult=findViewById(R.id.txtResult);
        btnCalculate=findViewById(R.id.btnCalculate);

        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                //edtFromDate.setText((CharSequence) calendar.getTime());

                updateCalender();
            }

            private void updateCalender(){
                String Format= "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                edtFromDate.setText(sdf.format(calendar.getTime()));
            }
        };

        edtFromDate.setOnClickListener(v ->{
            new DatePickerDialog(MainActivity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnCalculate.setOnClickListener(v->{
            String fromDateStr = edtFromDate.getText().toString();
            calculateDateDiffrence(fromDateStr);
        });
    }

    private void calculateDateDiffrence(String fromDateStr) {

        // define date format
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");

            try{

                LocalDate fromDate = LocalDate.parse(fromDateStr,formatter);
                LocalDate currentDate = LocalDate.now();

//                txtResult.setText(currentDate.toString());
                Toast.makeText(this, currentDate.toString(), Toast.LENGTH_SHORT).show();

                if(fromDate.isAfter(currentDate)){
                    Toast.makeText(this, "The from date cannot be in the future.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Period period=Period.between(fromDate,currentDate);

                int years = period.getYears();
                int months = period.getMonths();
                int days = period.getDays();

                String result = String.format("Difference : %d years, %d months, %d days",years,months,days);
                txtResult.setText(result);
            } catch (DateTimeParseException e){
                Toast.makeText(this, "Invalid date format. Please use DD-MM-YYYY.", Toast.LENGTH_SHORT).show();
            }
        //}

        //DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-mm-yyyy");


    }
}