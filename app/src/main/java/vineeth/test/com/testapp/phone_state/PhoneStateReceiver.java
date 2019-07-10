package vineeth.test.com.testapp.phone_state;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context,"inside phone state receiver",Toast.LENGTH_SHORT).show();
        if(intent.getAction().equalsIgnoreCase("android.intent.action.NEW_OUTGOING_CALL"))
        {
            Toast.makeText(context,"New OUT GOING call",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(context,"Incoming call",Toast.LENGTH_SHORT).show();
            int state = intent.getIntExtra(TelephonyManager.EXTRA_STATE,0);
            if(state==(TelephonyManager.CALL_STATE_RINGING))
            {
                Toast.makeText(context,"Incoming call ringing",Toast.LENGTH_SHORT).show();
            }else if(state == (TelephonyManager.CALL_STATE_OFFHOOK))
            {
                Toast.makeText(context,"Phone call received",Toast.LENGTH_LONG).show();
            }else if(state == TelephonyManager.CALL_STATE_IDLE)
            {
                Toast.makeText(context,"Call has been ended",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
