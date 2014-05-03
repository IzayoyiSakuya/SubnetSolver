package com.ms.subnetsolver;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.os.Build;

import com.ms.subnetsolver.*;

public class InputActivity extends ActionBarActivity {

	private boolean subnet_first;
	private AlertDialog debugDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		subnet_first = true;
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input, menu);
		
		// initialize group
   	 subnet_first = true;
   	 TextView netView = (TextView)InputActivity.this.findViewById(R.id.textView2);
   	 EditText netCapacity = (EditText)InputActivity.this.findViewById(R.id.subnet_capacity);
   	 netView.setEnabled(true);
   	 netCapacity.setEnabled(true);
   	 
   	 TextView hostView = (TextView)InputActivity.this.findViewById(R.id.TextView02);
   	 EditText hostCapacity = (EditText)InputActivity.this.findViewById(R.id.machine_capacity);
   	 hostView.setEnabled(false);
   	 hostCapacity.setEnabled(false);
		

		//根据ID找到RadioGroup实例
		RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup1);
		//绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {             
		             @Override
		             public void onCheckedChanged(RadioGroup arg0, int arg1) {
		                 // TODO Auto-generated method stub
		                 //获取变更后的选中项的ID
		                 int radioButtonId = arg0.getCheckedRadioButtonId();
		                 //根据ID获取RadioButton的实例
		                 RadioButton rb = (RadioButton)InputActivity.this.findViewById(radioButtonId);
		                 //更新文本内容，以符合选中项
		                 //tv.setText("您的性别是：" + rb.getText());
		                 if(rb.getText().equals("网段优先")){
		                	 subnet_first = true;
		                	 TextView netView = (TextView)InputActivity.this.findViewById(R.id.textView2);
		                	 EditText netCapacity = (EditText)InputActivity.this.findViewById(R.id.subnet_capacity);
		                	 netView.setEnabled(true);
		                	 netCapacity.setEnabled(true);
		                	 
		                	 TextView hostView = (TextView)InputActivity.this.findViewById(R.id.TextView02);
		                	 EditText hostCapacity = (EditText)InputActivity.this.findViewById(R.id.machine_capacity);
		                	 hostView.setEnabled(false);
		                	 hostCapacity.setEnabled(false);
		                	 
		                 }
		                 else{
		                	 subnet_first = false;
		                	 TextView netView = (TextView)InputActivity.this.findViewById(R.id.textView2);
		                	 EditText netCapacity = (EditText)InputActivity.this.findViewById(R.id.subnet_capacity);
		                	 netView.setEnabled(false);
		                	 netCapacity.setEnabled(false);
		                	 
		                	 TextView hostView = (TextView)InputActivity.this.findViewById(R.id.TextView02);
		                	 EditText hostCapacity = (EditText)InputActivity.this.findViewById(R.id.machine_capacity);
		                	 hostView.setEnabled(true);
		                	 hostCapacity.setEnabled(true);
		                	 
		                 }   
		             }
		});
		
		
		Button computeButton = (Button)InputActivity.this.findViewById(R.id.compute_subnet);
		computeButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
//				              switch (v.getId()) {
//				                 case R.id.mybutton: 
//				  //do something
//				                break;
//				                case R.id.mybutton2: 
//				 //do something
//				                break;
//				             }
				EditText ip_addr_text = (EditText)InputActivity.this.findViewById(R.id.netaddress);
				String ip_addr = ip_addr_text.getText().toString();
				EditText ip_mask_text = (EditText)InputActivity.this.findViewById(R.id.netmask);
				String ip_mask = ip_mask_text.getText().toString();
				
				EditText subnet_capacity_text = (EditText)InputActivity.this.findViewById(R.id.subnet_capacity);
				String subnet_capacity_string = subnet_capacity_text.getText().toString();
				
				EditText machine_capacity_text = (EditText)InputActivity.this.findViewById(R.id.machine_capacity);
				String machine_capacity_string = machine_capacity_text.getText().toString();
//				boolean validIP = SubnetSolverInfo.checkAddrValidate(ip_addr);
//				String s = "";
//				SubnetSolverInfo info = null;
//				if(validIP){
//					 info = new SubnetSolverInfo(SubnetSolverInfo.ip_string_to_int(ip_addr) , 24);
//					 //String desc = info.toString();
//					 SubnetSolverInfo[] infos = SubnetSolverInfo.calculate_subnet_by_net_count(info, 5);
//					 for(int i = 0; i < infos.length;i++){
//						 s = s + infos[i].toString();
//						 s = s + "\n";
//						 
//					 }
//					 
//					 //s = s + desc;
//				}
//				else{
//					s = "ip错误";
//					
//				}
//				
//				  Dialog alertDialog = new AlertDialog.Builder(InputActivity.this).
//						    setTitle("测试").
//						    setMessage(s).
//						    setIcon(R.drawable.ic_launcher).
//						    create();
//						  alertDialog.show();
				SubnetSolverInfo cc = new SubnetSolverInfo(SubnetSolverInfo.ip_string_to_int(ip_addr), 
						Integer.parseInt(ip_mask));
				if(subnet_first){
					cc.setSubnet_first(true);
					cc.setSubnet_capacity(Integer.parseInt(subnet_capacity_string));
				}
				else{
					cc.setSubnet_first(false);
					cc.setMachine_capacity(Integer.parseInt(machine_capacity_string));	
				}
				Intent intent = new Intent(InputActivity.this, ListActivity.class);  
				intent.putExtra("SubnetSolverInfo", cc);  
				startActivity(intent);  
				
				
			}
			
		});
		
		
		
		
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
			View rootView = inflater.inflate(R.layout.fragment_input,
					container, false);
			return rootView;
		}
	}

}
