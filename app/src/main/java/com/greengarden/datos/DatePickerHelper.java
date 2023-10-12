package com.greengarden.datos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerHelper implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Context context;
    private TextView editTextFecha;

    public DatePickerHelper(Context context, TextView editTextFecha) {
        this.context = context;
        this.editTextFecha = editTextFecha;
        this.editTextFecha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mostrarDatePicker();
    }

    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        editTextFecha.setText(fechaSeleccionada);
    }
}