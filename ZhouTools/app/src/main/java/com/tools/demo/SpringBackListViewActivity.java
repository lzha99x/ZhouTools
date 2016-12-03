package com.tools.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.widget.SpringBackListView;

import java.util.ArrayList;
import java.util.List;

public class SpringBackListViewActivity extends Activity {

	private SpringBackListView mListView;

	private List<String> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_springback);
		mListView = (SpringBackListView) findViewById(R.id.SpringBackListView);
		mList = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			mList.add(String.valueOf(i));
		}
		MyAdapter adapter = new MyAdapter();
		mListView.setAdapter(adapter);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(),
						R.layout.item_springback_lv, null);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

	}

	static class ViewHolder {
		ImageView image;
		TextView text;
	}

}
