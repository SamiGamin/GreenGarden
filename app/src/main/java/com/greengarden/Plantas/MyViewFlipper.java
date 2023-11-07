package com.greengarden.Plantas;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

public class MyViewFlipper {
    private ViewFlipper viewFlipper;

    public MyViewFlipper(Context context, ViewFlipper viewFlipper) {
        this.viewFlipper = viewFlipper;

        Animation fadeInAnimation = new AlphaAnimation(0f, 1f);
        fadeInAnimation.setDuration(3000); // Duración de 1 segundo

        Animation fadeOutAnimation = new AlphaAnimation(1f, 0f);
        fadeOutAnimation.setDuration(3000); // Duración de 1 segundo

        viewFlipper.setInAnimation(fadeInAnimation);
        viewFlipper.setOutAnimation(fadeOutAnimation);

        viewFlipper.setAutoStart(true); // Iniciar animación automáticamente
        viewFlipper.setFlipInterval(3000); // Intervalo de cambio de vistas (3 segundos)
        viewFlipper.startFlipping(); // Iniciar la animación
    }

    public void showNext() {
        viewFlipper.showNext();
    }

    public void showPrevious() {
        viewFlipper.showPrevious();
    }
}