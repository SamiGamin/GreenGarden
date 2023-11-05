package com.greengarden.Plantas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.greengarden.R;

public class Acelga extends AppCompatActivity {
    private EditText editTextNumero;
    private Button botonCalcular;

    private TableLayout tabla;
    private int cantidadPredefinidaAgua = 100; // Cantidad predefinida para el consumo de agua
    private int cantidadPredefinidaAbono = 10; // Cantidad predefinida para el consumo de abono
    private int totalAgua;
    private int totalAbono;
    private Button botonLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acelga);

        editTextNumero = findViewById(R.id.editTextNumero);
        botonCalcular = findViewById(R.id.botonCalcular);
        tabla = findViewById(R.id.tabla);
        botonLimpiar = findViewById(R.id.botonLimpiar);

        SharedPreferences sharedPreferences = getSharedPreferences("MisDatos", MODE_PRIVATE);
        totalAgua = sharedPreferences.getInt("totalAgua", 0);
        totalAbono = sharedPreferences.getInt("totalAbono", 0);

        TextView textViewResultado = findViewById(R.id.textViewResultados);
        String resultado = "Total: Milimetro de agua: " + totalAgua + "\nTotal: Gramos  de abono: " + totalAbono;


        textViewResultado.setText(resultado);

        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularValor();
                guardarDatos();
            }
        });

        botonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarTabla();
            }
        });
    }

    private void calcularValor() {
        String numeroTexto = editTextNumero.getText().toString();
        if (!numeroTexto.isEmpty()) {
            int numero = Integer.parseInt(numeroTexto);
            tabla.removeAllViews();

            TableRow encabezado = new TableRow(this);
            TextView columna1 = new TextView(this);
            columna1.setText("Insumos");
            columna1.setPadding(5, 5, 5, 5);
            encabezado.addView(columna1);

            TextView columna2 = new TextView(this);
            columna2.setText("Unidad");
            columna2.setPadding(5, 5, 5, 5);
            encabezado.addView(columna2);

            TextView columna3 = new TextView(this);
            columna3.setText("N° Plantas");
            columna3.setPadding(5, 5, 5, 5);
            encabezado.addView(columna3);

            TextView columna4 = new TextView(this);
            columna4.setText("Total");
            columna4.setPadding(5, 5, 5, 5);
            encabezado.addView(columna4);

            tabla.addView(encabezado);

            TableRow filaAgua = new TableRow(this);
            TextView textViewConsumoAgua = new TextView(this);
            textViewConsumoAgua.setText("Agua");
            textViewConsumoAgua.setPadding(5, 5, 5, 5);
            filaAgua.addView(textViewConsumoAgua);

            TextView textViewCantidadAgua = new TextView(this);
            textViewCantidadAgua.setText(String.valueOf(cantidadPredefinidaAgua));
            textViewCantidadAgua.setPadding(5, 5, 5, 5);
            filaAgua.addView(textViewCantidadAgua);

            TextView textViewCantidadPlantasAgua = new TextView(this);
            textViewCantidadPlantasAgua.setText(numeroTexto);
            textViewCantidadPlantasAgua.setPadding(5, 5, 5, 5);
            filaAgua.addView(textViewCantidadPlantasAgua);

            TextView textViewTotalAgua = new TextView(this);
            totalAgua = cantidadPredefinidaAgua * Integer.parseInt(numeroTexto);
            textViewTotalAgua.setText(String.valueOf(totalAgua));
            textViewTotalAgua.setPadding(5, 5, 5, 5);
            filaAgua.addView(textViewTotalAgua);

            tabla.addView(filaAgua);

            TableRow filaAbono = new TableRow(this);
            TextView textViewConsumoAbono = new TextView(this);
            textViewConsumoAbono.setText("Abono");
            textViewConsumoAbono.setPadding(5, 5, 5, 5);
            filaAbono.addView(textViewConsumoAbono);

            TextView textViewCantidadAbono = new TextView(this);
            textViewCantidadAbono.setText(String.valueOf(cantidadPredefinidaAbono));
            textViewCantidadAbono.setPadding(5, 5, 5, 5);
            filaAbono.addView(textViewCantidadAbono);

            TextView textViewCantidadPlantasAbono = new TextView(this);
            textViewCantidadPlantasAbono.setText(numeroTexto);
            textViewCantidadPlantasAbono.setPadding(5, 5, 5, 5);
            filaAbono.addView(textViewCantidadPlantasAbono);

            TextView textViewTotalAbono = new TextView(this);
            totalAbono = cantidadPredefinidaAbono * Integer.parseInt(numeroTexto);
            textViewTotalAbono.setText(String.valueOf(totalAbono));
            textViewTotalAbono.setPadding(5, 5, 5, 5);
            filaAbono.addView(textViewTotalAbono);

            tabla.addView(filaAbono);

            // Mostrar los resultados
            TextView textViewResultado = findViewById(R.id.textViewResultados);
            String resultado = "Total: Milimetros agua: " + totalAgua + ", Total: Gramos abono: " + totalAbono;
            textViewResultado.setText(resultado);
        }
    }

    private void guardarDatos() {
        String numeroTexto = editTextNumero.getText().toString();
        if (!numeroTexto.isEmpty()) {
            int numero = Integer.parseInt(numeroTexto);
            SharedPreferences sharedPreferences = getSharedPreferences("MisDatos", MODE_PRIVATE);
            int totalAguaGuardado = sharedPreferences.getInt("totalAgua", 0);
            int totalAbonoGuardado = sharedPreferences.getInt("totalAbono", 0);
            totalAgua = totalAguaGuardado + (cantidadPredefinidaAgua * numero);
            totalAbono = totalAbonoGuardado + (cantidadPredefinidaAbono * numero);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("totalAgua", totalAgua);
            editor.putInt("totalAbono", totalAbono);
            editor.apply();
        }
    }

    private void limpiarTabla() {
        tabla.removeAllViews();
        totalAgua = 0;
        totalAbono = 0;

        SharedPreferences sharedPreferences = getSharedPreferences("MisDatos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Borrar los datos almacenados en SharedPreferences
        editor.apply();

        TextView textViewResultado = findViewById(R.id.textViewResultados);
        textViewResultado.setText(""); // Borrar el contenido de textViewResultado
    }
}