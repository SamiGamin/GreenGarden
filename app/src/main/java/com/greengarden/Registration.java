package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    private TextView editTextFechaNacimiento;
    private DatePickerHelper datePickerHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        editTextFechaNacimiento = findViewById(R.id.rg_fecha);
        datePickerHelper = new DatePickerHelper(this, editTextFechaNacimiento);
        Spinner spinner = findViewById(R.id.spinner);
        SelectorOpcion selectorOpcion = new SelectorOpcion(spinner);
    }
}