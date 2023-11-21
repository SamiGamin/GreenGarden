package com.greengarden.Perfil;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.greengarden.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {

    private TextView Nombre, Apellidos, Email, vNombre, vApellidos;
    private EditText EdiNombre, EdiApellidos;
    private Button Editardatos, Guardardatos, EditarImagen;
    private CircleImageView ImagePerfil;
    private FirebaseAuth mAuth;
    private String userId;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        Nombre = findViewById(R.id.textnombre);
        vNombre = findViewById(R.id.vnombre);
        Apellidos = findViewById(R.id.textapellidos);
        vApellidos = findViewById(R.id.vapellidos);
        Email = findViewById(R.id.textemail);
        ImagePerfil = findViewById(R.id.imageperfil);
        Editardatos = findViewById(R.id.ediddatos);
        Guardardatos = findViewById(R.id.guardardatos);
        EditarImagen = findViewById(R.id.editarimagen);
        EdiNombre = findViewById(R.id.editnombre);
        EdiApellidos = findViewById(R.id.editapellidos);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
             userId = user.getUid();



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
            userReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserData userData = snapshot.getValue(UserData.class);

                    if (userData != null){
                        Nombre.setText(userData.getName());
                        Apellidos.setText(userData.getApellidos());
                        Email.setText(userData.getEmail());

                        String imagenperfilUrl = userData.getProfileImage();
                        if (imagenperfilUrl != null && !imagenperfilUrl.isEmpty()){
                            Picasso.get().load(imagenperfilUrl).into(ImagePerfil);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        Editardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               modoedicion(); 
            }
        });
        Guardardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                modificardatos();
            }
        });
        EditarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarimagen();
            }
        });
    }

    private void cargarimagen() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            // Aquí puedes mostrar la imagen seleccionada en un ImageView si es necesario
             ImagePerfil.setImageURI(selectedImageUri);

             if (userReference != null) {
                 subirimagen(selectedImageUri);
             }
        }
    }

    private void subirimagen(Uri selectedImageUri) {
// Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images");
        String imagenombre = userId + ".jpg" + ".png";
        StorageReference perfileImageRef = storageRef.child(imagenombre);
        perfileImageRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                perfileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String selectedImageUri = uri.toString();

                        userReference.child("profileImage").setValue(selectedImageUri);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Perfil.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void modificardatos() {
        String nuevoName = EdiNombre.getText().toString().trim();
        String nuevoapellido = EdiApellidos.getText().toString().trim();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", nuevoName);
        updates.put("apellidos", nuevoapellido);
        
        userRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    showViewMode();
                    Toast.makeText(Perfil.this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Perfil.this, "Error al guardar cambios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showViewMode() {
        Editardatos.setVisibility(View.VISIBLE);
        Nombre.setVisibility(View.VISIBLE);
        vNombre.setVisibility(View.VISIBLE);
        Apellidos.setVisibility(View.VISIBLE);
        vApellidos.setVisibility(View.VISIBLE);
        EditarImagen.setVisibility(View.GONE);
        Guardardatos.setVisibility(View.GONE);
        EdiNombre.setVisibility(View.GONE);
        EdiApellidos.setVisibility(View.GONE);

        EdiNombre.setText(Nombre.getText().toString());
        EdiApellidos.setText(Apellidos.getText().toString());
    }

    private void modoedicion() {
        Editardatos.setVisibility(View.GONE);
        Nombre.setVisibility(View.GONE);
        vNombre.setVisibility(View.GONE);
        Apellidos.setVisibility(View.GONE);
        vApellidos.setVisibility(View.GONE);
        EditarImagen.setVisibility(View.VISIBLE);
        Guardardatos.setVisibility(View.VISIBLE);
        EdiNombre.setVisibility(View.VISIBLE);
        EdiApellidos.setVisibility(View.VISIBLE);

        EdiNombre.setText(Nombre.getText().toString());
        EdiApellidos.setText(Apellidos.getText().toString());
    }
}