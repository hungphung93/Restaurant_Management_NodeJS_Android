package com.example.restaurantmanagement.Utilities;

import android.app.Activity;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.example.restaurantmanagement.R;
import com.pnikosis.materialishprogress.ProgressWheel;

public class LoadingSpinnerHelper {
    public static void displayLoadingSpinner(Activity activity){
        View loadingSpinner = activity.findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);
        ViewCompat.setTranslationZ(loadingSpinner, 5);
    }

    public static void hideLoadingSpinner(Activity activity){
        activity.findViewById(R.id.loading_spinner).setVisibility(View.INVISIBLE);
    }
}
