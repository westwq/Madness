package sg.edu.np.twq2.madness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
