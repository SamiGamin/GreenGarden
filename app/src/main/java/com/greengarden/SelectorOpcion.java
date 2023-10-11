package com.greengarden;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectorOpcion {
    private Spinner spinner;

    public SelectorOpcion(Spinner spinner) {
        this.spinner = spinner;
        inicializarSelector();
    }

    private void inicializarSelector() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinner.getContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                // Realizar acciones con la opción seleccionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se seleccionó ninguna opción
            }
        });
    }
}