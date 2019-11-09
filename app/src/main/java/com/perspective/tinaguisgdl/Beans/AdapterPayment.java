package com.perspective.tinaguisgdl.Beans;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import com.bixolon.printer.BixolonPrinter;
import com.perspective.tinaguisgdl.ActivityAsistencia;
import com.perspective.tinaguisgdl.ActivityCobro;
import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.MainActivity;
import com.perspective.tinaguisgdl.Model.Asistencia;
import com.perspective.tinaguisgdl.Model.Pagos;
import com.perspective.tinaguisgdl.R;

import java.util.ArrayList;

import static com.perspective.tinaguisgdl.ActivityCobro.mBixolonPrinter;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.ViewHolder> {

    public static ArrayList<ItemPayment> mDataSet;
    public static Context context;

    public AdapterPayment( Context context, ArrayList<ItemPayment> payment){
        this.mDataSet = payment;
        this.context = context;
    }

    public static Context getContext() {
        return context;
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

        double saldoD = 0d;

        saldoD = mDataSet.get(position).getSaldo() - mDataSet.get(position).getCobroTotal();

        holder.mPermisionarioNombre.setText( mDataSet.get(position).getNombrePermisionario());
        holder.mPuesto.setText(mDataSet.get(position).getPuesto());
        holder.mTianguis.setText(mDataSet.get(position).getTianguis());
        holder.mMetrosLineales.setText(String.valueOf(mDataSet.get(position).getMetrosLineales()));
        holder.mSaldoAntes.setText(String.valueOf(mDataSet.get(position).getSaldo()));
        holder.mSaldoDespues.setText(String.valueOf(saldoD));
        holder.mCobroTotal.setText(String.valueOf(mDataSet.get(position).getCobroTotal()));
        holder.btnImprimir.setVisibility(View.GONE);

        boolean pago;
        pago = mDataSet.get(position).isPago();

        if(pago) {
            holder.mCobrar.setVisibility(View.GONE);
        }else {
            holder.btnImprimir.setVisibility(View.VISIBLE);
        }

        holder.mCobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("click","button");
                Double saldo = 0d,saldoa = 0d,total = 0d;

                saldo = mDataSet.get(position).getSaldoDespues();
                saldoa = mDataSet.get(position).getSaldo();
                total = mDataSet.get(position).getCobroTotal();


                ContentValues cv = new ContentValues();
                cv.put("saldo",saldo);
                cv.put("estatus","N");
                System.err.print(ActivityCobro.db.update(ActivityCobro.gestion.TABLE_PERMISIONARIO,cv,"id = " + mDataSet.get(position).getIPermisio(),null) + " update");
                Pagos pagos = new Pagos(mDataSet.get(position).getIPermisio(),mDataSet.get(position).getIdTianguis(),"PAGO","N",total,saldoa,0,saldo);
                Log.v("pagos",ActivityCobro.gestion.insertarPagos(ActivityCobro.db,pagos) + " <-");
                holder.btnImprimir.setVisibility(View.VISIBLE);
                holder.mCobrar.setVisibility(View.GONE);
            }
        });

        holder.btnImprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBixolonPrinter = new BixolonPrinter(context, ActivityCobro.mHandler, null);
                mBixolonPrinter.findBluetoothPrinters();

                AlertDialog dialog = null;

                ActivityCobro.showQrCodeDialog(dialog, context);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        Button mCobrar,btnImprimir;
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
            btnImprimir = v.findViewById(R.id.btnImprimir);

        }
    }

}