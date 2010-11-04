package com.back2arie.bnismsbanking;

import com.back2arie.bnismsbanking.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class Setting extends ListActivity {
	private String[] menu;
	private ListView list_menu;
	private Integer pilihan;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);	
	       list_menu = getListView();
	       list_menu.setTextFilterEnabled(true);	       
	       menu = getResources().getStringArray(R.array.setting_array);
	       setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menu));
	       list_menu.setOnItemClickListener(new OnItemClickListener() {
	    	   @Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
	    	   {
	    		   pilihan = ((Long) id).intValue();
	    		   switch(pilihan)
	    		   {	    		   
	    		   		case 0: // Ubah password
    		   				   LayoutInflater factory = LayoutInflater.from(Setting.this);
    		   			       final View textEntryView = factory.inflate(R.layout.input_password, null);
    		   			       final EditText editText = (EditText) textEntryView .findViewById(R.id.pass_BNI);

    		   			       AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
	    		        	   builder.setView(textEntryView);
	    		        	   String title = getResources().getString(R.string.ubah_pass);
	    		        	   String pesan = getResources().getString(R.string.input_pass);
	    		        	   builder.setTitle(title)
	    		        	   		  .setMessage(pesan)
	    		        	          .setCancelable(false)
	    		        	          .setPositiveButton(getResources().getString(R.string.tombol_lanjut), new DialogInterface.OnClickListener() {
	    		        	              @Override
										public void onClick(DialogInterface dialog, int id) {
	    		        	            	  String pass_BNI = editText.getText().toString().trim();
	    		        	                  SharedPreferences settings = getPreferences(0);
	    		        	                  SharedPreferences.Editor editor = settings.edit();
	    		        	                  
	    		        	                  editor.putString("pass_BNI", pass_BNI);
	    		        	                  editor.commit();
	    		        	              }
	    		        	          })
	    		        	          .setNegativeButton(getResources().getString(R.string.tombol_batal), new DialogInterface.OnClickListener() {
	    		        	              @Override
										public void onClick(DialogInterface dialog, int id) {
	    		        	                   dialog.cancel();
	    		        	              }
	    		        	          });
	    		        	   AlertDialog alert = builder.create(); 
	    		        	   alert.show();
	    		   		break;
	    		   
	    		   }
	    	   }
	       });
	}
}
