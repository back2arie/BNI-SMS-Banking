package com.back2arie.bnismsbanking;

import com.back2arie.bnismsbanking.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IsiPulsa extends ListActivity {
	private  String kode_master_trx;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	       
		kode_master_trx = this.getIntent().getExtras().getString("kode");
		String[] menu = getResources().getStringArray(R.array.operator_pulsa);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, menu));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Integer pilihan = ((Long) id).intValue();
				Intent nominal = new Intent(view.getContext(), NominalPulsa.class);
				Bundle bundle = new Bundle();
				bundle.putInt("operator", pilihan);
				bundle.putString("kode", kode_master_trx);
				nominal.putExtras(bundle);
				startActivity(nominal);
			}
		});
	}
}
