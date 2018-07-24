package sg.edu.np.twq2.madness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase fbDB;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
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

                dbRef.child("users").setValue(x);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
