package com.back2arie.bnismsbanking;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.content.Context;

public class BNISMS extends ListActivity {	
	private ListView list_menu;
	private String[] menu;
	private String[] kode;
	private String kode_terpilih;
	private Integer pilihan;
	private AlertDialog konfirmasi;
	private DialogInterface.OnClickListener ok_listener;
	public static final String PREFS_NAME = "MyPrefsFile";
	
	public class MyCustomAdapter extends ArrayAdapter<String> {

		public MyCustomAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
		}

		// Layouting
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.menu_awal, parent, false);
			TextView label = (TextView)row.findViewById(R.id.list_menu);
			label.setText(menu[position]);
			ImageView icon = (ImageView)row.findViewById(R.id.list_icon);

			switch (position) {
			 	case 0:
			 		icon.setImageResource(R.drawable.cek_saldo);
			 	break;
			 	case 1:
			 		icon.setImageResource(R.drawable.history_transaksi);
			 	break;
			 	case 2:
			 		icon.setImageResource(R.drawable.transfer);
			 	break;
			 	case 3:
			 		icon.setImageResource(R.drawable.cek_tagihan);
			 	break;	
			 	case 4:
			 		icon.setImageResource(R.drawable.pembayaran);
			 	break;	
			 	case 5:
			 		icon.setImageResource(R.drawable.isi_pulsa);
			 	break;		 		
			}
			 return row;
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       list_menu = getListView();
       list_menu.setTextFilterEnabled(true);
       menu = getResources().getStringArray(R.array.menu_array);
       kode = getResources().getStringArray(R.array.menu_code);
       
       final DialogInterface.OnClickListener cancel_listener = new DialogInterface.OnClickListener() {
       @Override
       public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
           }
       };
       
       setListAdapter(new MyCustomAdapter(BNISMS.this, R.layout.menu_awal, menu));
       list_menu.setOnItemClickListener(new OnItemClickListener() {
    	   @Override
    	   public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
    	   {
    		   pilihan = ((Long) id).intValue();
    		   switch(pilihan)
    		   {
					case 0:	// Cek saldo
						   konfirmasi = new AlertDialog.Builder(BNISMS.this).create();
						   konfirmasi.setTitle(getResources().getString(R.string.konfirmasi));
						   konfirmasi.setMessage(getResources().getString(R.string.konf_cek_saldo));
						   
						   ok_listener = new DialogInterface.OnClickListener() {
					              @Override
								public void onClick(DialogInterface dialog, int id) {
					            	  // Kirim SMS
					            	  kode_terpilih = kode[pilihan];
						              String message = kode_terpilih;	    		        	                
						              	    		        	              
						   			  Intent sms = new Intent(view.getContext(), KirimSMS.class);
						   			  sms.putExtra("message", message);
						   			  startActivity(sms); 
					              }
					       };
						   
						   konfirmasi.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.tombol_lanjut), ok_listener);
						   konfirmasi.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.tombol_batal), cancel_listener);
						   konfirmasi.show();
					break;

					case 1:	// History Transaksi
						   konfirmasi = new AlertDialog.Builder(BNISMS.this).create();
						   konfirmasi.setTitle(getResources().getString(R.string.konfirmasi));
						   konfirmasi.setMessage(getResources().getString(R.string.konf_cek_history));
						   
						   ok_listener = new DialogInterface.OnClickListener() {
					              @Override
								public void onClick(DialogInterface dialog, int id) {
					            	  // Kirim SMS
					            	  kode_terpilih = kode[pilihan];
						              String message = kode_terpilih;	    		        	                
						              	    		        	              
						   			  Intent sms = new Intent(view.getContext(), KirimSMS.class);
						   			  sms.putExtra("message", message);
						   			  startActivity(sms); 
					              }
					       };
						   
						   konfirmasi.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.tombol_lanjut), ok_listener);
						   konfirmasi.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.tombol_batal), cancel_listener);
						   konfirmasi.show();
					break;
					
					case 2: // Transfer     		   			
						Intent transfer = new Intent(view.getContext(), MenuTransfer.class);
						kode_terpilih = kode[pilihan];
						transfer.putExtra("kode", kode_terpilih);
						startActivity(transfer);    		   			
					break;
					
					case 3: // Tagihan
						Intent tagihan = new Intent(view.getContext(), MenuTagihan.class);
						kode_terpilih = kode[pilihan];
						tagihan.putExtra("kode", kode_terpilih);
						startActivity(tagihan);    		   			
					break;
					
					case 4: // Pembayaran
					    Intent pembayaran = new Intent(view.getContext(), Pembayaran.class);
					    kode_terpilih = kode[pilihan];
					    pembayaran.putExtra("kode", kode_terpilih);
					    startActivity(pembayaran);    		   			
					break;   	  
					    
					case 5: // Isi ulang pulsa
					    Intent pulsa = new Intent(view.getContext(), IsiPulsa.class);
					    kode_terpilih = kode[pilihan];
					    pulsa.putExtra("kode", kode_terpilih);        	            
					    startActivity(pulsa);    		   			
					break;
	        	 }
    	   	}
       	});
   }
	   
@Override
public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.my_menu, menu);
       return true;
   }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
       // Handle item selection
       switch (item.getItemId()) {          
       case R.id.item02:
    	   menuAbout();
           return true;
       default:
           return super.onOptionsItemSelected(item);
       }
   }

protected void menuAbout() {
    	final DialogInterface.OnClickListener cancel_listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
                 dialog.cancel();
            }
        };
	   AlertDialog about = new AlertDialog.Builder(BNISMS.this).create();
	   about.setTitle(getResources().getString(R.string.title_about));
	   String message = "(Unofficial) " + getResources().getString(R.string.app_name_long) + " " + getResources().getString(R.string.app_version) + "\n\n" + 
	   					getResources().getString(R.string.app_author) + "\n<" + getResources().getString(R.string.author_email) + ">";
	   about.setMessage(message);
	   about.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.tombol_ok), cancel_listener);
	   about.show();
   }

}