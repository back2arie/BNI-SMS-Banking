package com.back2arie.bnismsbanking;

import com.back2arie.bnismsbanking.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NominalPulsa extends ListActivity {
	private Integer OID;
	private String[] kode_operator;
	private String[] nama_operator;
	private String kode_master_trx;
	private String kode_terpilih;
	private String nominal_angka;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       
	       OID = this.getIntent().getExtras().getInt("operator");
	       kode_master_trx = this.getIntent().getExtras().getString("kode");
	       String itemName = "nominal_pulsa_" + OID;	
	       nama_operator = getResources().getStringArray(R.array.operator_pulsa);
	       kode_operator = getResources().getStringArray(R.array.kode_operator_pulsa);
	       
	       this.setTitle(getResources().getString(R.string.isi_ulang_pulsa) + nama_operator[OID]);
	       
	       int itemsid = getResources().getIdentifier(itemName, "array", getPackageName());
	       String[] menu = getResources().getStringArray(itemsid);	       
	       setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menu));
	       ListView list_menu = getListView();
	       list_menu.setTextFilterEnabled(true);

	       final DialogInterface.OnClickListener cancel_listener = new DialogInterface.OnClickListener() {
	           @Override
			public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       };
	       
	       list_menu.setOnItemClickListener(new OnItemClickListener() {
	           @Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
	           {
	        	   AlertDialog isi_pulsa = new AlertDialog.Builder(NominalPulsa.this).create();
	        	   final EditText input = new EditText(NominalPulsa.this);
	        	   isi_pulsa.setView(input);
	        	   final String nominal = ((TextView) view).getText().toString();
	        	   
	        	   DialogInterface.OnClickListener ok_listener = new DialogInterface.OnClickListener() {
     	              @Override
					public void onClick(DialogInterface dialog, int id) {
     	            	  
		            	  String no_ponsel = input.getText().toString().trim();
		            	  String delimiter = getResources().getString(R.string.delimiter);
		            	  kode_terpilih = kode_operator[OID];
		            	  
		            	  // Konversi nominal string ke angka
		            	  String[] temp = nominal.trim().split(" ");		            	  
		            	  if(temp[1].equals("Ribu")) {		            		  
		            		  nominal_angka = temp[0] + "000";
		            	  }
		            	  else if(temp[1].equals("Juta")) {
		            		  nominal_angka = temp[0] + "000000";
		            	  }
		            	  
	    	              final String message = kode_master_trx + delimiter + kode_terpilih + delimiter + no_ponsel + delimiter + nominal_angka;
		            	  
	        	      		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
	        	                @Override
								public void onClick(DialogInterface dialog, int id) {
		        	            	  // Kirim SMS
			      		   			  Intent sms = new Intent(view.getContext(), KirimSMS.class);
			      		   			  sms.putExtra("message", message);
			      		   			  startActivity(sms);
	        	                }
	        	            };
	    	              
	        	      		AlertDialog alert = new AlertDialog.Builder(NominalPulsa.this).create();
	        	    		alert.setTitle(getResources().getString(R.string.konfirmasi));
	        	    		String isi_konfirmasi = getResources().getString(R.string.konf_isi_ulang_pulsa) + "\n" + nama_operator[OID] + "\n\n" +
	        	    								getResources().getString(R.string.konf_no_ponsel_pulsa) + "\n" + no_ponsel + "\n\n" +
	        	    								getResources().getString(R.string.konf_nominal_pulsa) + "\n" + nominal;
	        	    		
	        	    		alert.setMessage(isi_konfirmasi);
	        	    		alert.setCancelable(false);
	        	    		alert.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.tombol_lanjut), listener);
	        	    		alert.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.tombol_batal), cancel_listener);
	        	    		alert.show();
     	              }
     	          	};
	        	   
	        	   String title = getResources().getString(R.string.isi_ulang_pulsa) + nama_operator[OID];
	        	   isi_pulsa.setMessage(getResources().getString(R.string.input_no_ponsel));
	        	   isi_pulsa.setTitle(title);
	        	   isi_pulsa.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.tombol_lanjut), ok_listener);
	        	   isi_pulsa.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.tombol_batal), cancel_listener);
	        	   isi_pulsa.show();
	           }
	         });       
	      
	   }
}
