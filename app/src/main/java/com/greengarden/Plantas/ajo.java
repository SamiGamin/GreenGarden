package com.greengarden.Plantas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ViewFlipper;
import com.greengarden.R;
import com.greengarden.Menu.MenuClickListener;
//menu
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
//menu
public class ajo extends AppCompatActivity {
    //menu
    private Button menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajo);
//inicio menu
        menuButton = findViewById(R.id.btn_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ajo.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(ajo.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
//fin menu
        ViewFlipper viewFlipper = findViewById(R.id.home_slider);
        MyViewFlipper myViewFlipper = new MyViewFlipper(this, viewFlipper);
    }

}
