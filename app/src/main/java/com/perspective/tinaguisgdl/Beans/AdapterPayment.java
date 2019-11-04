package com.perspective.tinaguisgdl.Beans;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.perspective.tinaguisgdl.MainActivity;
import com.perspective.tinaguisgdl.R;

import java.util.ArrayList;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.ViewHolder> {

    private ArrayList<ItemPayment> mDataSet;
    private Context context;

    public AdapterPayment( Context context, ArrayList<ItemPayment> payment){
        this.mDataSet = payment;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterPayment.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Falta Saldo despues y descuento

        holder.mPermisionarioNombre.setText( mDataSet.get(position).getNombrePermisionario());
        holder.mPuesto.setText(mDataSet.get(position).getPuesto());
        holder.mTianguis.setText(mDataSet.get(position).getTianguis());
        holder.mMetrosLineales.setText(String.valueOf(mDataSet.get(position).getMetrosLineales()));
        holder.mSaldoAntes.setText(String.valueOf(mDataSet.get(position).getSaldo()));
        holder.mSaldoDespues.setText(String.valueOf(mDataSet.get(position).getMetrosLineales()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        Button mCobrar;
        TextView mPermisionarioNombre;
        TextView mPuesto;
        TextView mTianguis;
        TextView mMetrosLineales;
        TextView mSaldoAntes;
        TextView mSaldoDespues;
        TextView mDescuento;
        TextView mCobroTotal;


        ViewHolder(View v) {
            super(v);

            mCobrar = v.findViewById(R.id.btn_registrar_cobro);
            mPermisionarioNombre = v.findViewById(R.id.tv_nombrePermisionario);
            mPuesto = v.findViewById(R.id.tv_puesto);
            mTianguis = v.findViewById(R.id.tv_nombre_tianguis);
            mMetrosLineales = v.findViewById(R.id.tv_metros);
            mSaldoAntes = v.findViewById(R.id.tv_saldo_antes);
            mSaldoDespues = v.findViewById(R.id.tv_saldo_despues);
            mDescuento = v.findViewById(R.id.tv_cobro_total);
            mCobroTotal = v.findViewById(R.id.tv_cobro_total);
        }
    }

}