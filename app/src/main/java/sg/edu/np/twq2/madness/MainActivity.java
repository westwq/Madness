package sg.edu.np.twq2.madness;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements FbDbFactory.FbDbFactoryRead {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * @see https://www.androidhive.info/2017/12/android-working-with-bottom-navigation/
         * @see https://www.truiton.com/2017/01/android-bottom-navigation-bar-example/
         */
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.mEvents:
                                selectedFragment = new EventFragment();
                                break;
                            default:
                                selectedFragment = new GroupFragment();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, new GroupFragment());
        transaction.commit();

        //new FbDbFactory(this).readData("userGroups", MadConstants.getId(), "123", "Check123" );
    }

    public void clickScan(View v)
    {
        //Intent in = new Intent(this, QRScannerActivity.class);
        //startActivityForResult(in, IntentIntegrator.REQUEST_CODE);
        new IntentIntegrator(this)
                .setCaptureActivity(QRScannerActivity.class)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                .setPrompt("")
                .initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result =   IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this,    "Cancelled",Toast.LENGTH_LONG).show();
            } else {
                String x = result.getContents();
                ((TextView)findViewById(R.id.txtResult)).setText(x);

                //new FbDbFactory().writeData("userGroups", MadConstants.getId(), x, "true");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void readReturns(String tag, DataSnapshot dataSnapshot) {
        if(tag.equals("Check123"))
        {
            String value = dataSnapshot.getValue(String.class);
            ((TextView)findViewById(R.id.txtResult)).setText("" + value);
        }
    }

    @Override
    public void readFailure(String tag, DatabaseError error) {
        //read failure
    }
}
