package pe.com.moviles.broadcasting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txtBateria;
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBateria = (TextView) this.findViewById(R.id.tvBateria);
        txtStatus = (TextView) this.findViewById(R.id.tvStatus);
        //this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        this.registerReceiver(InformacionBateriaReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver InformacionBateriaReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            txtBateria.setText(String.valueOf(level) + "%");
            txtStatus.setText(String.valueOf((isCharging?"Cargando......":"Descargado...")));

            //Toast.makeText(getApplicationContext() ,String.valueOf((isCharging?"Cargando":"Descargando")) + "%", Toast.LENGTH_SHORT).show();
        }
    };
}