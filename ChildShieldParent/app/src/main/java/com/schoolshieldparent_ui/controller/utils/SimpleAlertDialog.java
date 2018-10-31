package com.schoolshieldparent_ui.controller.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.Toast;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.view.activity.Activity_ForumAppDetail;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

public class SimpleAlertDialog {
    private Dialog dialogAlert;

    @SuppressLint("NewApi")
    public static void showAlertDialog(Activity activity, String message) {

        final Dialog dialog = new Dialog( activity );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.view_simple_alert );
        TextView_Regular text = (TextView_Regular) dialog.findViewById( R.id.message );

        try {
            Spannable wordtoSpan = new SpannableString( message );
            int firstSlashNPosition = message.indexOf( "\n" );
            wordtoSpan.setSpan( new RelativeSizeSpan( 1.2f ), 0, firstSlashNPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            text.setText( wordtoSpan );
        } catch (NullPointerException e) {
            text.setText( message );
        } catch (IndexOutOfBoundsException e) {
            text.setText( message );
        } catch (Exception e) {
            text.setText( message );
        }
        Button_Regular dialogButton = (Button_Regular) dialog.findViewById( R.id.button );
        dialogButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );

        dialog.show();

    }
    public static void showSimpleAlertDialog(final Activity activity, String message) {

        final Dialog dialogAlert = new Dialog( activity );
        dialogAlert.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialogAlert.setContentView( R.layout.view_simple_alert );
        TextView_Regular text = (TextView_Regular) dialogAlert.findViewById( R.id.message );
        text.setText( message );

        final Button_Regular dialogButton = (Button_Regular) dialogAlert.findViewById( R.id.button );
        dialogButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
                activity.finish();
            }
        } );
        dialogAlert.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );

        dialogAlert.show();

    }
    public static void showAlertDialogService(Context context, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder( context ).setTitle( "Title" ).setMessage( "Are you sure?" ).create();

        alertDialog.getWindow().setType( WindowManager.LayoutParams.TYPE_SYSTEM_ALERT );
        alertDialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );

        alertDialog.show();
    }

    public static void showAlertDialogRate(final Activity activity, final String rating) {
        final Dialog dialog = new Dialog( activity );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setCancelable( true );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        dialog.setContentView( R.layout.view_rate_dialog );

        final RatingBar ratingBar = (RatingBar) dialog.findViewById( R.id.ratingBar5 );


        if (rating.equalsIgnoreCase( "1.00" )) {
            ratingBar.setRating( 1 );
        } else if (rating.equalsIgnoreCase( "2.00" )) {
            ratingBar.setRating( 2 );
        } else if (rating.equalsIgnoreCase( "3.00" )) {
            ratingBar.setRating( 3 );
        } else if (rating.equalsIgnoreCase( "4.00" )) {
            ratingBar.setRating( 4 );
        } else if (rating.equalsIgnoreCase( "5.00" )) {
            ratingBar.setRating( 5 );
        }
        ratingBar.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating() == 1) {
                    ratingBar.setRating( 0 );
                }
            }
        } );
        Button dialogButton = (Button) dialog.findViewById( R.id.buttonRate );
        dialogButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ratingBar.getRating() == 0) {
                    Toast.makeText( activity, "Select at least one color", Toast.LENGTH_SHORT ).show();
                } else {
                    dialog.dismiss();
                    Activity_ForumAppDetail.getInstance().addRatingToApp( ratingBar.getRating() + "" );
                }
            }
        } );


        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.show();
        dialog.getWindow().setLayout( (6 * width) / 7, LayoutParams.WRAP_CONTENT );

    }

    public void showAlertDialogChild(Activity activity, String message) {
        checkDialogShowing();
        dialogAlert = new Dialog( activity );
        dialogAlert.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialogAlert.setContentView( R.layout.view_simple_alert );
        TextView_Regular text = (TextView_Regular) dialogAlert.findViewById( R.id.message );
        text.setText( message );

        Button_Regular dialogButton = (Button_Regular) dialogAlert.findViewById( R.id.button );
        dialogButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlert.dismiss();
            }
        } );
        dialogAlert.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );

        dialogAlert.show();

    }

    public void checkDialogShowing() {
        if (dialogAlert != null) {
            dialogAlert.dismiss();
            dialogAlert.isShowing();
        }
    }


}
