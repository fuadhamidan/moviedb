package com.fuadhamidan.moviedb.util;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.util
 * -Desc Class
 */
public class DialogFactory {
    public static Dialog createSimpleOkErrorDialog(Context context, int message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", null);
        return alertDialog.create();
    }

    public static Dialog createSimpleOkErrorDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", null);
        return alertDialog.create();
    }

    public static Snackbar createSimpleSnackbarDone(View view, int message, int length){
        final Snackbar snackbar = Snackbar.make(view, message, length);

        snackbar.setAction("Done", new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

        return snackbar;
    }
}
