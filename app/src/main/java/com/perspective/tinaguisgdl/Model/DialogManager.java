package com.perspective.tinaguisgdl.Model;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;

import com.perspective.tinaguisgdl.ActivityAsistencia;
import com.perspective.tinaguisgdl.ActivityCobro;

import java.util.Set;

public class DialogManager {

    public static void showBluetoothDialog(Context context, final Set<BluetoothDevice> pairedDevices) {
        final String[] items = new String[pairedDevices.size()];
        int index = 0;
        for (BluetoothDevice device : pairedDevices) {
            items[index++] = device.getAddress();
        }

        new AlertDialog.Builder(context).setTitle("Paired Bluetooth printers").setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ActivityAsistencia.mBixolonPrinter.connect(items[which]);
            }
        }).show();
    }

    public static void showBluetoothDialog1(Context context, final Set<BluetoothDevice> pairedDevices) {
        final String[] items = new String[pairedDevices.size()];
        int index = 0;
        for (BluetoothDevice device : pairedDevices) {
            items[index++] = device.getAddress();
        }

        new AlertDialog.Builder(context).setTitle("Paired Bluetooth printers").setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ActivityCobro.mBixolonPrinter.connect(items[which]);
            }
        }).show();
    }

}
