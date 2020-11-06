package com.campfiregames.neverhave;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import static com.campfiregames.neverhave.IntentHelper.createIntentForGooglePlay;
import static com.campfiregames.neverhave.PreferenceHelper.getIsAgreeShowDialog;
import static com.campfiregames.neverhave.PreferenceHelper.setAgreeShowDialog;
import static com.campfiregames.neverhave.PreferenceHelper.setAppLaunchedTimes;

final class DialogManager {

    private Dialog dialog;

    public DialogManager(final Context context, final DialogOptions options) {
        dialog = create(context, options);
        dialog.show();
    }

    private Dialog create(final Context context, final DialogOptions options) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(options.getCancelable());

        final View view = options.getView();
        if (view != null) builder.setView(view);

        Button positive = view.findViewById(R.id.btnOk);
        Button negative = view.findViewById(R.id.btnCancel);


        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intentToAppstore = createIntentForGooglePlay(context);
                context.startActivity(intentToAppstore);
                setAgreeShowDialog(context, false);
                dialog.dismiss();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAgreeShowDialog(context, false);
                dialog.dismiss();
            }
        });


        if (getIsAgreeShowDialog(context)){
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    setAppLaunchedTimes(context, 0);
                }
            });
        }

        return builder.create();
    }
}