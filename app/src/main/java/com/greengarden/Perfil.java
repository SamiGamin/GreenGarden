package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
    }
    public void crearBaseDeDatos(View view) {
        // Obtén una referencia a la base de datos
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Crea un objeto para almacenar datos
        Map<String, Object> planta = new HashMap<>();
        planta.put("nombre", "Planta A");
        planta.put("fechaSiembra", "2023-10-01");
        planta.put("descripcion", "Esta es la planta A, información adicional aquí");
        planta.put("riego", "Riega cada 10 días.");
        planta.put("luzSolar", "Puede crecer en sombra parcial.");
        planta.put("temperatura", "Temperatura ideal entre 22-28°C.");
        planta.put("humedad", "Tolera la sequedad del suelo.");

        // Agrega más datos según sea necesario

        // Escribe los datos en la base de datos bajo el nodo "plantas"
        String plantaId = databaseReference.child("plantas").push().getKey(); // Genera una clave única para la planta
        databaseReference.child("plantas").child(plantaId).setValue(planta);

        Toast.makeText(this, "Base de datos creada y datos agregados", Toast.LENGTH_SHORT).show();
    }
}