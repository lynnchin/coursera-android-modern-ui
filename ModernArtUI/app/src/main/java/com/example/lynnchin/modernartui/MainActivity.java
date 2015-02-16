package com.example.lynnchin.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity{


    private SeekBar colorBar;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById( R.id.mainLayout);
        colorBar = (SeekBar)findViewById(R.id.colorBar);
        colorBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                 for(int index =0; index < mainLayout.getChildCount(); index++){
                        if(progress > 0) {
                            View childView = mainLayout.getChildAt(index);
                            int originalColor = Color.parseColor((String) childView.getTag());
                            int invertedColor = (0x00FFFFFF - (originalColor | 0xFF000000)) | (originalColor & 0xFF000000);
                            childView.setBackgroundColor(changeColor(originalColor, invertedColor, progress));
                        }else{
                            // Reset back to original color if the progress reaches zero
                            View childView = mainLayout.getChildAt(index);
                            int originalColor = Color.parseColor((String) childView.getTag());
                            childView.setBackgroundColor(originalColor);
                        }
                 }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private int changeColor(int originalColor, int invertedColor, int progress){

            int deltaR = Color.red(invertedColor) - Color.red(originalColor);
            int deltaG = Color.green(invertedColor) - Color.green(originalColor);
            int deltaB = Color.blue(invertedColor) - Color.blue(originalColor);

            int newR = Color.red(originalColor) + (deltaR * progress / 100);
            int newG = Color.red(originalColor) + (deltaG * progress / 100);
            int newB = Color.blue(originalColor) + (deltaB * progress/ 100);

            return Color.rgb(newR,newG,newB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Show AlertDialogFragment
             MoreInfoDialogFragment.newInstance().show(getFragmentManager(), "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class MoreInfoDialogFragment extends DialogFragment {

        public static MoreInfoDialogFragment newInstance() {
            return new MoreInfoDialogFragment();
        }

        // Build AlertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Inspired by the works of artists such as Piet Mondrian and Ben Nicholson.\n\n\nClick below to learn more!")

                            // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                            // Set up No Button
                    .setNegativeButton("Not Now", null)

                            // Set up Yes Button
                    .setPositiveButton("Visit MOMA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    Intent visitURL = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moma.org"));
                                    startActivity(visitURL);
                                }
                            }).create();
        }
    }
}


