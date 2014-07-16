package com.example.notepad;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements View.OnCreateContextMenuListener, View.OnClickListener, DialogInterface.OnClickListener, OnKeyListener {

	EditText textView;
	Button addButton;
	ListView listItems;
	
	ArrayList <String> toDoList;
	ArrayAdapter <String> adapter;
	
	String[] Option_1;
	String[] Option_2 = {"A_2", "B_2", "C_2", "D_2"};
	String[] Option_3 = {"A_3", "B_3", "C_3", "D_3"};
	String[] Option_4 = {"A_4", "B_4", "C_4", "D_4"};
	String optionItem;
	
	private static final String TAG = "MyActivity";
	
	private static String currentMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v(TAG, "onCreate");
		setContentView(R.layout.activity_main);
		
		Option_1 = getResources().getStringArray(R.array.Options_1_Array);
				
		textView = (EditText) findViewById(R.id.textView);
		addButton = (Button) findViewById(R.id.addButton);
		listItems = (ListView) findViewById(R.id.listItems);
		
		addButton.setOnClickListener(this);
		textView.setOnKeyListener(this);
		
		toDoList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoList);
		
		listItems.setAdapter(adapter);
		
		registerForContextMenu(listItems);
		
	}
	
	public void addItem(String Item) {
		if(Item.length() > 0) {
			Toast.makeText(getApplicationContext(), Item+" added", Toast.LENGTH_SHORT).show();
			this.toDoList.add(Item);
			this.adapter.notifyDataSetChanged();
			this.textView.setText("");
		}
	}
	
	private void displayPopUp(String optionItem, String[] items) {
		Log.v(TAG, "displayPopUp");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(optionItem);
		builder.setItems(items, this);
		builder.show();
	}
	
	private void delete(int itemId) {
		if(itemId == android.widget.AdapterView.INVALID_POSITION) {
			Toast.makeText(getApplicationContext(), "No item Selected", Toast.LENGTH_SHORT).show();
		}
		else if(itemId > 0) {
			String itemName = (String)listItems.getItemAtPosition(itemId);
			
			Toast.makeText(getApplicationContext(), itemName+" deleted!", Toast.LENGTH_SHORT).show();
			this.toDoList.remove(itemId);
			this.adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.add("Delete");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		
		if(item.getTitle() == "Delete") {
			int index = info.position;
			this.delete(index);
		}
		
		return true;
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(getResources().getString( R.string.Option_1_name));
		menu.add("Option_2");
		menu.add("Option_3");
		menu.add("Option_4");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.v(TAG, "opOtionsItemSelected");
		super.onOptionsItemSelected(item);
		
		if (item.hasSubMenu() == false) {
			if(item.getTitle() == getResources().getString(R.string.Option_1_name)) {
				currentMenu = getResources().getString(R.string.Option_1_name);
				this.displayPopUp(getResources().getString(R.string.Option_1_name), this.Option_1);
			}
			if(item.getTitle() == "Option_2") {
				currentMenu = "Option_2";
				this.displayPopUp("Option_2", this.Option_2);
			}
			if(item.getTitle() == "Option_3") {
				currentMenu = "Option_3";
				this.displayPopUp("option_3", this.Option_3);
			}
			if(item.getTitle() == "Option_4") {
				currentMenu = "Option_4";
				this.displayPopUp("option_4", this.Option_4);
			}
		}
		
		return true;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			this.addItem(this.textView.getText().toString());
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Log.v(TAG, "View onClick");
		if(v == this.addButton){
		this.addItem(this.textView.getText().toString());
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		Log.v(TAG, "DialogInterface onClick");
		if(currentMenu == getResources().getString(R.string.Option_1_name)) {
			this.addItem(Option_1[which]);
		}
		if(currentMenu == "Option_2") {
			this.addItem(Option_2[which]);
		}
		if(currentMenu == "Option_3") {
			this.addItem(Option_3[which]);
		}
		if(currentMenu == "Option_4") {
			this.addItem(Option_4[which]);
		}
	}
}
