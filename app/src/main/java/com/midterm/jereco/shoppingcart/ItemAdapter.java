package com.midterm.jereco.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jereco on 9/3/2016.
 */
public class ItemAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private int mResource;
    private List<Product> mList;
    public ItemAdapter(Context context, int resource, List<Product> product) {
        super(context, resource, product);

        mContext = context;
        mResource = resource;
        mList = product;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;

        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        }else{
            viewholder =(ViewHolder)convertView.getTag();
        }
        Product product = mList.get(position);
        if(product!=null){
            if(viewholder.mName != null){
                viewholder.mName.setText(product.getName());
            }

            if(viewholder.mQuantity != null){
                viewholder.mQuantity.setText(product.getQuantity()+"");
            }

            if(viewholder.mPrice != null){
                viewholder.mPrice.setText("Php "+String.format("%,.2f",product.getPrice()));
            }

            if(viewholder. mTotal!= null){
                viewholder.mTotal.setText("Php "+String.format("%,.2f",product.getTotalPrice()));
            }
        }
        return convertView;
    }

    public class ViewHolder{
        TextView mName;
        TextView mQuantity;
        TextView mPrice;
        TextView mTotal;
        String data;

        public ViewHolder(View view){
            mName = (TextView) view.findViewById(R.id.txtName);
            mQuantity = (TextView) view.findViewById(R.id.txtQuantity);
            mPrice = (TextView) view.findViewById(R.id.txtPrice);
            mTotal = (TextView) view.findViewById(R.id.txtSubTotal);
        }
    }
}
