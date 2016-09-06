package com.midterm.jereco.shoppingcart;

import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        mScanView = new ZXingScannerView(this);
        setContentView(mScanView);
        mScanView.setResultHandler(this);
        mScanView.startCamera();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mScanView.stopCamera();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        mScanView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        String res = result.getText();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    mScanView.stopCamera();
                    sleep(500);
                    finish();
                } catch (InterruptedException e) {

                }
            }
        };
        if (res.matches("([\\w\\s()\"-]*\\|\\|\\|[0-9]+\\|\\|\\|[0-9]+\\.?[0-9]+)")) {
            String[] data = res.split("\\|\\|\\|");
            if ((!data[1].matches("\\.")) && Integer.parseInt(data[1]) > 0 && Double.parseDouble(data[2]) > 0) {
                alert.setTitle("Scanning Complete!");
                alert.setMessage("Data added successfully.");
                mScanView.stopCamera();

                Intent intent = new Intent();
                intent.putExtra("QR_DATA", data);
                setResult(RESULT_OK, intent);


            } else {
                Toast toast = null;
                toast.makeText(ScannerActivity.this,"Invalid Data!\nPlease scan again.",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast toast = null;
            toast.makeText(ScannerActivity.this, "Invalid Data!\nPlease scan again.", Toast.LENGTH_SHORT).show();
        }
        alert.show();
        thread.start();
    }
}
