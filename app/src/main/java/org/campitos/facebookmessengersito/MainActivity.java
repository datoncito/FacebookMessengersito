package org.campitos.facebookmessengersito;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.messenger.MessengerThreadParams;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;


public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;
    private Toolbar mToolbar;
    private View mMessengerButton;
    private MessengerThreadParams mThreadParams;
    private boolean mPicking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mMessengerButton = findViewById(R.id.messenger_send_button);

        mToolbar.setTitle(R.string.app_name);
        //
        Intent intent = getIntent();
        if (Intent.ACTION_PICK.equals(intent.getAction())) {
            mThreadParams = MessengerUtils.getMessengerThreadParamsForIntent(intent);
            mPicking = true;

        }

        mMessengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMessengerButtonClicked();
            }
        });
    }

    private void onMessengerButtonClicked() {
        // Esta uri puede referenciar muchas cosas como imagenes, archivos de sonido etc
        try {
            Uri uri =
                    Uri.parse("android.resource://org.campitos.facebookmessengersito/" + R.drawable.raton);
            //Uri.parse("http://campitos-ley.whelastic.net/soluciones/introduccion.jpg");

            // Creamos los parametros de lo que queremos compratir en messenger
            ShareToMessengerParams shareToMessengerParams =
                    ShareToMessengerParams.newBuilder(uri, "image/png")
                            .setMetaData("{ \"image\" : \"tree\" }")
                            .build();

            if (mPicking) {

                MessengerUtils.finishShareToMessenger(this, shareToMessengerParams);
            } else {
                //Si mno esta instalado en messenger aqui saldra un emnsaje muy bonito
                MessengerUtils.shareToMessenger(
                        this,
                        REQUEST_CODE_SHARE_TO_MESSENGER,
                        shareToMessengerParams);
            }
        }catch (Exception e){
            System.out.println("ALGO MALO PASOOOOOO");
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
