package com.back2arie.bnismsbanking;

import com.back2arie.bnismsbanking.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.text.NumberFormat;

public class Pembayaran extends ListActivity {
	private ListView list_menu;
	private String[] menu;
	private String[] kode_pembayaran;
	private String[] perlu_amount;
	private String kode_master_pembayaran;
	private Integer pilihan;
	private EditText input_jumlah_bayar;
	private DialogInterface.OnClickListener ok_listener;
	private String message;
	private String jumlah_bayar_hum;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       kode_master_pembayaran = this.getIntent().getExtras().getString("kode");
       kode_pembayaran = getResources().getStringArray(R.array.kode_pembayaran);
       perlu_amount = getResources().getStringArray(R.array.perlu_amount);
       menu = getResources().getStringArray(R.array.menu_pembayaran);		
       setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menu));

       final DialogInterface.OnClickListener cancel_listener = new DialogInterface.OnClickListener() {
		    @Override
			public void onClick(DialogInterface dialog, int id) {
		         dialog.cancel();
		    }
       };
		
       list_menu = getListView();
       list_menu.setTextFilterEnabled(true);
       
       list_menu.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
           {        	   
        	   final EditText input_no_tagihan;
        	   
        	   pilihan = ((Long) id).intValue();
        	   AlertDialog tagihan = new AlertDialog.Builder(Pembayaran.this).create();
               
        	   if (perlu_amount[pilihan].equals("yes")) {
	        	   LayoutInflater factory = LayoutInflater.from(Pembayaran.this);
	               final View textEntryView = factory.inflate(R.layout.input_pembayaran, null);
	               input_no_tagihan = (EditText) textEntryView .findViewById(R.id.no_tagihan); 
	               input_jumlah_bayar = (EditText) textEntryView .findViewById(R.id.jumlah_bayar);        	   
	        	   tagihan.setView(textEntryView);
        	   }
        	   else {
        		   input_no_tagihan = new EditText(Pembayaran.this);
        		   tagihan.setView(input_no_tagihan);
	        	   String pesan = getResources().getString(R.string.input_no_tagihan);
	        	   tagihan.setMessage(pesan);
        	   }
        	   
        	   ok_listener = new DialogInterface.OnClickListener() {
 	              @Override
 	              public void onClick(DialogInterface dialog, int id) {
 	            	  String jumlah_bayar = "";
 	            	  String no_tagihan = input_no_tagihan.getText().toString().trim();
 	            	  if (perlu_amount[pilihan].equals("yes")) {
 	            		  jumlah_bayar = input_jumlah_bayar.getText().toString().trim();
	        	          
 	            		  // Format jumlah transfer ke format currency
	        	          NumberFormat nf = NumberFormat.getInstance();
	        	          jumlah_bayar_hum = nf.format(Integer.parseInt(jumlah_bayar));
 	            	  }
 	            	  
 	            	  String delimiter = getResources().getString(R.string.delimiter);
 	            	  String kode_terpilih = kode_pembayaran[pilihan];
     	              message = kode_master_pembayaran + delimiter + kode_terpilih + delimiter + no_tagihan;
     	              if (perlu_amount[pilihan].equals("yes")) {
     	            	 message = message + delimiter + jumlah_bayar;
     	              }
     	          
	      	      		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
	    	                @Override
							public void onClick(DialogInterface dialog, int id) {
	        	            	  // Kirim SMS
		      		   			  Intent sms = new Intent(view.getContext(), KirimSMS.class);
		      		   			  sms.putExtra("message", message);
		      		   			  startActivity(sms);
	    	                }
	    	            };
     	              
						AlertDialog alert = new AlertDialog.Builder(Pembayaran.this).create();
						alert.setTitle(getResources().getString(R.string.konfirmasi));
						String isi_konfirmasi = getResources().getString(R.string.konf_pembayaran) + menu[pilihan] + "\n\n" +
												getResources().getString(R.string.konf_no_tagihan) + "\n" + no_tagihan;   
						
						if (perlu_amount[pilihan].equals("yes")) {
							isi_konfirmasi = isi_konfirmasi + "\n\n" + getResources().getString(R.string.konf_nominal_pembayaran) + "\n" + jumlah_bayar_hum;
						}
												
						alert.setMessage(isi_konfirmasi);
						alert.setCancelable(false);
						alert.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.tombol_lanjut), listener);
						alert.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.tombol_batal), cancel_listener);
						alert.show(); 
 	              }
 	          };
 	                  	   
	    	   String title = new StringBuilder(getResources().getString(R.string.konf_pembayaran)).append(((TextView) view).getText()).toString();
	    	   tagihan.setTitle(title);
	    	   tagihan.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.tombol_lanjut), ok_listener);
	    	   tagihan.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.tombol_batal), cancel_listener);
	    	   tagihan.show();
           }
         });       
   }
}
