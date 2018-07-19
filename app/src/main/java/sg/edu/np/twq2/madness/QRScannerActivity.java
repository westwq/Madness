package sg.edu.np.twq2.madness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class QRScannerActivity extends
        /*AppCompatActivity {

            private CaptureManager capture;
            private DecoratedBarcodeView scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        scanner = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        capture = new CaptureManager(this, scanner);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }*/
        CaptureActivity {
    @Override
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.activity_qrscanner);
        return (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }
}
