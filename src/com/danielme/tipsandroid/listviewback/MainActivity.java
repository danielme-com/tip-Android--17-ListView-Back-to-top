package com.danielme.tipsandroid.listviewback;

import java.util.ArrayList;
import java.util.List;

import com.danieme.tipsandroid.listviewback.R;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;


/**
 * 
 * @author danielme.com
 *
 */
public class MainActivity extends ListActivity
{
	private View backToTop;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		List<String> items = new ArrayList<String>(3);
		
		for (int i =0;i<30;i++)
		{
			items.add("Item " + i);
		}
		
		//obtenemos el footer y establecemos su listener
		backToTop = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.back_to_top_list_view, null, false);
		backToTop.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				if (Build.VERSION.SDK_INT < 11) //método estándar
				{
					getListView().setSelection(0);
				}
				else //sólo disponible a partir de Honeycomb
				{
					getListView().smoothScrollToPositionFromTop(0, 0);
					//se puede especificar la duración de la animación ne ms
					//getListView().smoothScrollToPositionFromTop(0, 0,1000);
				}				
			}
		});
		//el footer siempre se añade antes que el adapter, luego se puede mostrar y ocultar cuando se quiera
		getListView().addFooterView(backToTop);
		
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1, items));
		//no se muestra el footer haste que no sepamos si es necesario
		getListView().removeFooterView(backToTop);
		
		getListView().post(new Runnable() { //se averigua si el footer es necesario, esto es, hay elementos que no se muestran en pantalla
			   public void run() {
				   int visible = MainActivity.this.getListView().getLastVisiblePosition();
				   int total = MainActivity.this.getListAdapter().getCount();
				   if (visible + 1 < total)
				   {
					   MainActivity.this.getListView().addFooterView(MainActivity.this.backToTop);
				   }
			   }
			});
	}
}