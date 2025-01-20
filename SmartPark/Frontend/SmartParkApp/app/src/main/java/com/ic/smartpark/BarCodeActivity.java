package com.ic.smartpark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarCodeActivity extends AppCompatActivity {

    ImageView barCode;
    public static final String BARCODE="BARCODE";
    String barCodeVal;
    MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bar_code);
        Intent i=getIntent();
        barCodeVal=i.getStringExtra(BARCODE);
        barCode=findViewById(R.id.barCodeImg);
        getCode();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void barcode() throws WriterException{
        BitMatrix bitMatrix=multiFormatWriter.encode(barCodeVal, BarcodeFormat.CODE_128,3000,1000,null);
        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
        Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
        changeColor(bitmap);
        barCode.setImageBitmap(bitmap);
    }
    public void getCode(){
        try{
            barcode();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeColor(Bitmap myBitmap) {

        int [] allpixels = new int [myBitmap.getHeight()*myBitmap.getWidth()];

        myBitmap.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());

        for(int i = 0; i < allpixels.length; i++)
        {
            if(allpixels[i] == Color.WHITE)
            {
                allpixels[i] = Color.TRANSPARENT;
            }
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(BarCodeActivity.this,MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}