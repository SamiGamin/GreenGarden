package com.greengarden.Plantas;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

public class MyViewFlipper {

    private ViewFlipper viewFlipper;

    public MyViewFlipper(Context context, ViewFlipper viewFlipper) {
        this.viewFlipper = viewFlipper;

        AnimationSet slideInAnimation = new AnimationSet(true);
        slideInAnimation.addAnimation(new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f));
        slideInAnimation.addAnimation(new AlphaAnimation(0f, 1f));
        slideInAnimation.setDuration(500);

        AnimationSet slideOutAnimation = new AnimationSet(true);
        slideOutAnimation.addAnimation(new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, -1f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f));
        slideOutAnimation.addAnimation(new AlphaAnimation(1f, 0f));
        slideOutAnimation.setDuration(500);

        viewFlipper.setInAnimation(slideInAnimation);
        viewFlipper.setOutAnimation(slideOutAnimation);

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