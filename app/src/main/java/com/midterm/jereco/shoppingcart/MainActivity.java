package com.midterm.jereco.shoppingcart;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mAdd;
    private ImageButton mEmail;
    private ImageButton mText;
    private TextView mTotal;
    private TextView mEmpty;
    private Double FinalTotal = 0.00;
    private ListView mList;
    private Product product = null;
    private List<Product> cart = new ArrayList<>();
    private String message = "-=+Shopping List+=-\n\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdd = (ImageButton)findViewById(R.id.btnAdd);
        mEmail = (ImageButton)findViewById(R.id.btnEmail);
        mText = (ImageButton)findViewById(R.id.btnText);
        mTotal = (TextView)findViewById(R.id.txtFinalTotal);
        mEmpty = (TextView)findViewById(R.id.txtEmpty);
        mList = (ListView)findViewById(R.id.ItemList);

        mAdd.setOnClickListener(this);
        mEmail.setOnClickListener(this);
        mText.setOnClickListener(this);
    }

    @Override

    public void onClick(View v) {
        String message = "";
        switch(v.getId()) {
            case R.id.btnAdd:
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnEmail:
                if(cart.isEmpty()) {
                    Toast toast = null;
                    toast.makeText(MainActivity.this,"List is Empty",Toast.LENGTH_LONG).show();
                }else {
                    Intent email = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:?subject= Shopping List &body=" + this.message+"TOTAL: Php "+String.format("%,.2f",FinalTotal));
                    email.setData(data);
                    startActivity(email);
                }
                break;
            case R.id.btnText:
                if(cart.isEmpty()) {
                    Toast toast = null;
                    toast.makeText(MainActivity.this,"List is Empty",Toast.LENGTH_LONG).show();
                }else {
                    Intent text = new Intent(Intent.ACTION_VIEW);
                    text.setData(Uri.parse("sms:"));
                    text.putExtra("sms_body",this.message+"TOTAL: Php "+String.format("%,.2f",FinalTotal));
                    text.setType("vnd.android-dir/mms-sms");
                    startActivity(text);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1) {
            if (resultCode == RESULT_OK) {
                String[] arr = data.getStringArrayExtra("QR_DATA");
                Double total = Integer.parseInt(arr[1])*Double.parseDouble(arr[2]);
                product = new Product(arr[0],
                        Integer.parseInt(arr[1]),
                        Double.parseDouble(arr[2]),
                        total);

                cart.add(product);
                message+="Item: "+arr[0]+"\n"+
                        "Quantity: "+arr[1]+"\n"+
                        "Price: Php "+String.format("%,.2f",product.getPrice())+"\n"+
                        "Total Price: Php  "+String.format("%,.2f",total)+"\n\n";
                mEmpty.setText("");

                FinalTotal+=total;
                mTotal.setText("Php "+String.format("%,.2f",FinalTotal));

                ItemAdapter adapter = new ItemAdapter(this,R.layout.order_list,cart);
                mList.setAdapter(adapter);
            }
        }
    }
}
