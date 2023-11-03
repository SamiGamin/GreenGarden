package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.greengarden.Consejos.Consejos;
import com.greengarden.Cultivo.huerto;

import java.util.ArrayList;

public class Agregar_Cultivos extends AppCompatActivity {

    private Button buttonNavigate;
    private Spinner spinnerProducts;
    private Button buttonAddToCart;
    private ListView listViewCart;
    private ArrayList<String> cartItems;
    private ArrayAdapter<String> cartAdapter;


    Button menu, Btnhuerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cultivos);
        buttonNavigate = findViewById(R.id.buttonNavigate);


        // Obtener referencias de los elementos de la interfaz
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        spinnerProducts = findViewById(R.id.spinnerProducts);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);
        TextView textViewCart = findViewById(R.id.textViewCart);
        listViewCart = findViewById(R.id.listViewCart);

        // Configurar el título y los productos del catálogo
        textViewTitle.setText("Agregar nuevas plantas al Huerto");
        String[] products = {"Variedad de plantas", "Zanahoria", "Ajo", "Remolacha", "Lechuga", "Acelga", "Albahaca", "Hierbabuena", "Hinojo", "Fastron", "Laurel"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, products);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducts.setAdapter(spinnerAdapter);

        // Configurar el carrito de compras
        cartItems = new ArrayList<>();
        cartAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartItems);
        listViewCart.setAdapter(cartAdapter);


        menu = findViewById(R.id.btn_menu);
        Btnhuerto = findViewById(R.id.buttonNavigate);

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = spinnerProducts.getSelectedItem().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                int totalElementos = sharedPreferences.getInt("totalElementos", 0);
                boolean alreadyAdded = false;
                for (int i = 1; i <= totalElementos; i++) {
                    String seleccion = sharedPreferences.getString("seleccion" + i, "");
                    if (seleccion.equals(selectedItem)) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    cartItems.add(selectedItem);
                    cartAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Planta agregada al huerto", Toast.LENGTH_SHORT).show();
                    // Guardar la selección en SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("seleccion" + (totalElementos + 1), selectedItem);
                    editor.putInt("totalElementos", totalElementos + 1);
                    editor.apply();
                } else {
                    Toast.makeText(getApplicationContext(), "La planta ya está en el huerto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Agregar_Cultivos.this, huerto.class);
                startActivity(intent);
            }
        });


        Btnhuerto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ircuidados = new Intent(Agregar_Cultivos.this, huerto.class);
                startActivity(ircuidados);
            }
        });


        //comienso de menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Agregar_Cultivos.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.navigation_menu, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Toast.makeText(Agregar_Cultivos.this, " " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        if (id == R.id.inicio) {
                            Intent inicio = new Intent(Agregar_Cultivos.this, Inicio.class);
                            startActivity(inicio);
                            return true;
                        }
                        if (id == R.id.consumo) {

                            Intent consumo = new Intent(Agregar_Cultivos.this, Cuidados.class);
                            startActivity(consumo);
                            return true;
                        }
                        if (id == R.id.estadisticas) {
                            Intent estadisticas = new Intent(Agregar_Cultivos.this, Estadisticas.class);
                            startActivity(estadisticas);
                            return true;
                        }
                        if (id == R.id.consejos) {
                            Intent consejos = new Intent(Agregar_Cultivos.this, Consejos.class);
                            startActivity(consejos);
                            return true;
                        }
                        if (id == R.id.cultivos) {
                            Intent cultivos = new Intent(Agregar_Cultivos.this, Agregar_Cultivos.class);
                            startActivity(cultivos);
                            return true;
                        }
                        if (id == R.id.comu_even) {
                            Intent eventos = new Intent(Agregar_Cultivos.this, ComunidadEventos.class);
                            startActivity(eventos);
                            return true;
                        }
                        if (id == R.id.salir) {
                            finish();
                            return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        // fin de menu

    }
}