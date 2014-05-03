package com.ms.subnetsolver;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class ListActivity extends ActionBarActivity {

	
	
	private SubnetSolverInfo[] infos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		
	     Intent intent = getIntent();  
	     SubnetSolverInfo cc = (SubnetSolverInfo)intent.getSerializableExtra("SubnetSolverInfo");    
		if(cc.isSubnet_first()){
			infos = SubnetSolverInfo.calculate_subnet_by_net_count(cc, cc.getSubnet_capacity());
		}
		else{
			infos = SubnetSolverInfo.calculate_subnet_by_host_count(cc, cc.getMachine_capacity());	
		}
		
		//绑定Layout里面的ListView  
       ListView list = (ListView) findViewById(R.id.list);  
         
       //生成动态数组，加入数据  
       ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();  
       for(int i=0;i<infos.length;i++)  
       {  
           HashMap<String, Object> map = new HashMap<String, Object>();    
           map.put("ItemTitle", infos[i].toString());               
           listItem.add(map);  
       }  
       //生成适配器的Item和动态数组对应的元素  
       SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
           R.layout.listview_layout,//ListItem的XML实现  
           //动态数组与ImageItem对应的子项          
           new String[] {"ItemTitle"},   
           //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
           new int[] {R.id.ItemTitle}  
       );  
        
       //添加并且显示  
       list.setAdapter(listItemAdapter);  
       
       //添加点击  
       list.setOnItemClickListener(new OnItemClickListener() {  
 
           @Override  
           public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                   long arg3) {  
               //setTitle("点击第"+arg2+"个项目");
        	   SubnetSolverInfo cc = infos[arg2];
        	   Intent intent = new Intent(ListActivity.this, DetailActivity.class);  
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
			View rootView = inflater.inflate(R.layout.fragment_list, container,
					false);
			return rootView;
		}
	}

}
