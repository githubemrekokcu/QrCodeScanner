package com.emrekokcu.developer.qrcodescanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {
    private Button btn_QrAndBarcode_Scanner;
    private TextView txt_scannerResults;
    private ImageView img_qr_and_barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        registerEventHandlers();
    }


    @SuppressLint("CutPasteId")
    private void initComponents() {
        btn_QrAndBarcode_Scanner = findViewById(R.id.btn_scanner);
        img_qr_and_barcode = findViewById(R.id.imgview_qr_and_barcode);
        txt_scannerResults = findViewById(R.id.txtContent);
    }

    private void registerEventHandlers() {
        btn_QrAndBarcode_Scanner_onClick();
        txt_scannerResults_onClick();
        bindData();
    }

    private void txt_scannerResults_onClick() {
        txt_scannerResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt_scannerResults.getText().toString().isEmpty()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(txt_scannerResults.getText().toString()));
                    startActivity(i);
                }

            }
        });

    }

    private void bindData() {
        img_qr_and_barcode.setImageResource(R.drawable.qrcode);
    }

    private void btn_QrAndBarcode_Scanner_onClick() {
        btn_QrAndBarcode_Scanner.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "Assert"})
            @Override
            public void onClick(View v) {
                Bitmap myBitmap = BitmapFactory.decodeResource(
                        getApplicationContext().getResources(),
                        R.drawable.qrcode);
                img_qr_and_barcode.setImageBitmap(myBitmap);
                BarcodeDetector detector =
                        new BarcodeDetector.Builder(getApplicationContext())
                                .setBarcodeFormats(Barcode.ALL_FORMATS)
                                .build();
                if (!detector.isOperational()) {
                    txt_scannerResults.setText("Could not set up the detector!");
                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);

                Barcode thisCode = barcodes.valueAt(0);
                String str_result = thisCode.rawValue;
                assert !str_result.isEmpty();
                txt_scannerResults.setText(str_result);
                txt_scannerResults.setTextColor(Color.BLUE);
                txt_scannerResults.setPaintFlags(txt_scannerResults.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
        });

    }

}
