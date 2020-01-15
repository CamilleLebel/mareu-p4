package com.example.maru.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public abstract class ShowMessage {

    // METHODS -------------------------------------------------------------------------------------

    public static void showMessageWithSnackbar(@NonNull CoordinatorLayout coordinatorLayout, @NonNull String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showMessageWithToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
