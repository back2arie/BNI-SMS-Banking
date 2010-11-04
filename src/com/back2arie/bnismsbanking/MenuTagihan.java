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

public class MenuTagihan extends ListActivity {
	private ListView list_menu;
	private String[] menu;
	private String[] kode_tagihan;
	private String kode_master_tagihan;
	private Integer pilihan;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       kode_master_tagihan = this.getIntent().getExtras().getString("kode");
       kode_tagihan = getResources().getStringArray(R.array.kode_tagihan);
       menu = getResources().getStringArray(R.array.menu_tagihan);
       setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menu));

       list_menu = getListView();
       list_menu.setTextFilterEnabled(true);
       
       list_menu.setOnItemClickListener(new OnItemClickListener() {
           @Override
		public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
           {        	   
        	   pilihan = ((Long) id).intValue();
        	   
        	   AlertDialog tagihan = new AlertDialog.Builder(MenuTagihan.this).create();
        	   final EditText input = new EditText(MenuTagihan.this);
        	   tagihan.setView(input);
 
        	   DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
 	              @Override
				public void onClick(DialogInterface dialog, int id) {
 	            	  String no_tagihan = input.getText().toString().trim();
 	            	  String delimiter = getResources().getString(R.string.delimiter);
 	            	  String kode_terpilih = kode_tagihan[pilihan];
     	              String message = kode_master_tagihan + delimiter + kode_terpilih + delimiter + no_tagihan;
 	            	  	        	
 	            	  // Kirim SMS
   		   			  Intent sms = new Intent(view.getContext(), KirimSMS.class);
   		   			  sms.putExtra("message", message);
   		   			  startActivity(sms); 
 	              }
 	          };
 	          
				DialogInterface.OnClickListener cancel_listener = new DialogInterface.OnClickListener() {
				    @Override
					public void onClick(DialogInterface dialog, int id) {
				         dialog.cancel();
				    }
				};
        	   
	    	   String title = new StringBuilder(getResources().getString(R.string.konf_cek_tagihan)).append(((TextView) view).getText()).toString();
	    	   String pesan = getResources().getString(R.string.input_no_tagihan);
	    	   tagihan.setTitle(title);
	    	   tagihan.setMessage(pesan);
	    	   tagihan.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.tombol_lanjut), listener);
	    	   tagihan.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.tombol_batal), cancel_listener);
	    	   tagihan.show();
           }
         });       
   }
}
