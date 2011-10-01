package com.back2arie.bnismsbanking;

import com.back2arie.bnismsbanking.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
//import android.util.Log;


public class KirimSMS extends Activity {
	ProgressThread progressThread;
	ProgressDialog progressDialog;
	String phoneNo;
	String message;
	Boolean status;
	public static final String PREFS_NAME = "MyPrefsFile";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
       	super.onCreate(savedInstanceState);
       	
       	phoneNo = getResources().getString(R.string.no_BNI);
       	message = this.getIntent().getExtras().getString("message");
       	
        progressDialog = new ProgressDialog(KirimSMS.this);
        progressDialog.setTitle(getResources().getString(R.string.loading));
        progressDialog.setMessage(getResources().getString(R.string.mengirim_sms));
        progressDialog.show();
        
        progressThread = new ProgressThread(handler);
        progressThread.start();
	}
	
	protected void showConfirmation(String msg)
	{
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int id) {
          	  KirimSMS.this.finish();
            }
        };
		
		AlertDialog alert = new AlertDialog.Builder(KirimSMS.this).create();
		alert.setTitle(getResources().getString(R.string.notifikasi));
		alert.setMessage(msg);
		alert.setCancelable(false);
		alert.setButton("OK", listener);
		alert.show();	
	}
	
    // Handler untuk Thread
    final Handler handler = new Handler() {
    	String pesan;
    	
        @Override
		public void handleMessage(Message msg) {
            status = msg.getData().getBoolean("status");
                 	            
            if(status) {
            	pesan = getResources().getString(R.string.sms_berhasil_terkirim);
            }
            else {
            	pesan = getResources().getString(R.string.sms_gagal_terkirim);
            }
            progressDialog.dismiss();            
            showConfirmation(pesan);
        }
    };

    private class ProgressThread extends Thread {
        Handler mHandler;
       
        ProgressThread(Handler h) {
            mHandler = h;
        }
       
        @Override
		public void run() {
			try {
			    Thread.sleep(500); /* FIXME */
			} 
			catch (InterruptedException e) {
				// Show exception
			    //Log.e("ERROR", "Thread Interrupted");
			}
			
			final Message msg = mHandler.obtainMessage();
			final Bundle b = new Bundle();
            
		   // Kirim SMS
		   String SENT = "SMS_SENT";
		   PendingIntent sentPI = PendingIntent.getBroadcast(KirimSMS.this, 0, new Intent(SENT), 0);
		   
		   // Listener untuk memeriksa apakah SMS berhasil/gagal terkirim
		   BroadcastReceiver smsListener = new BroadcastReceiver() {
		       @Override
			public void onReceive(Context context, Intent intent) {
		           if (getResultCode() == Activity.RESULT_OK) {
		               b.putBoolean("status", true);
		           }
				   else {
					   b.putBoolean("status", false);
				   }
		           msg.setData(b);
		           mHandler.sendMessage(msg);
		       }
		   };
		   IntentFilter filter = new IntentFilter(SENT);
		   registerReceiver(smsListener, filter);
		
		   SmsManager sms = SmsManager.getDefault();
		   sms.sendTextMessage(phoneNo, null, message, sentPI, null); 
        }
    }
}
