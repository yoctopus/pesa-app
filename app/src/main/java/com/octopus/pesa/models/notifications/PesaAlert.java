package com.octopus.pesa.models.notifications;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

/**
 * Created by octopus on 6/29/16.
 */
public class PesaAlert extends AlertDialog.Builder {
    protected PesaAlert(@NonNull Context context) {
        super(context);
    }

    public PesaAlert(Context context, String message) {
        this(context);
        this.setMessage(message);

    }

    /**
     * Construct an AlertDialog that uses an explicit theme.  The actual style
     * that an AlertDialog uses is a private implementation, however you can
     * here supply either the name of an attribute in the theme from which
     * to get the dialog's style (such as {@link }.
     *
     * @param context
     * @param themeResId
     */
    protected PesaAlert(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public void showAlert() {
        AlertDialog dialog = this.create();
        dialog.show();
    }

}
