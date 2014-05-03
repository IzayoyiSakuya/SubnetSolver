package com.ms.subnetsolver;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class DetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		
		Intent intent = getIntent();  
	     SubnetSolverInfo cc = (SubnetSolverInfo)intent.getSerializableExtra("SubnetSolverInfo");
	     
	     int ip = cc.getNet_address_int();
	     int mask = cc.getNet_mask_int();
	     
	     String ip_str = SubnetSolverInfo.ip_int_to_string(cc.getNet_address_int());
	     TextView ip_str_view = (TextView)DetailActivity.this.findViewById(R.id.subnet_address);
	     ip_str_view.setText(ip_str);
	     
	     
	     
	     String ip_bit_str = String.valueOf(mask);
	     TextView ip_bit_str_view = (TextView)DetailActivity.this.findViewById(R.id.mask_length);
	     ip_bit_str_view.setText(ip_bit_str);	     
	     
	     
	     String ip_mask_str = SubnetSolverInfo.ip_int_to_string(SubnetSolverInfo.bit_to_mask(mask));
	     TextView ip_mask_str_view = (TextView)DetailActivity.this.findViewById(R.id.subnet_mask);
	     ip_mask_str_view.setText(ip_mask_str);
	     
	     
	     
	     int capacity = (0x1 << (32-mask)) - 2;
	     String capacity_str = String.valueOf(capacity);
	     TextView capacity_str_view = (TextView)DetailActivity.this.findViewById(R.id.capacity);
	     capacity_str_view.setText(capacity_str);
	     
	     int available_ip_start = ip + 1;
	     String available_ip_start_str = SubnetSolverInfo.ip_int_to_string(available_ip_start);
	     TextView available_start_str_view = (TextView)DetailActivity.this.findViewById(R.id.available_begin);
	     available_start_str_view.setText(available_ip_start_str);
	     
	     int available_ip_end = ip + capacity - 1;
	     String available_ip_end_str = SubnetSolverInfo.ip_int_to_string(available_ip_end);
	     TextView available_end_str_view = (TextView)DetailActivity.this.findViewById(R.id.available_end);
	     available_end_str_view.setText(available_ip_end_str);		
		
		
		return true;
		
		
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail,
					container, false);
			return rootView;
		}
	}

}
