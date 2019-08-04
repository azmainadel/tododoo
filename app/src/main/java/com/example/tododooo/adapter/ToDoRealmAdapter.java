package com.example.tododooo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tododooo.R;
import com.example.tododooo.model.ToDoItem;
import com.example.tododooo.utility.Colors;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

//
// Created by Azmain Adel on 8/4/19.
//

public class ToDoRealmAdapter
        extends RealmBasedRecyclerViewAdapter<ToDoItem, ToDoRealmAdapter.ViewHolder> {

    public class ViewHolder extends RealmViewHolder {
        public TextView toDoItemTextView;

        public ViewHolder(FrameLayout container) {
            super(container);
            this.toDoItemTextView = (TextView) container.findViewById(R.id.todo_item_text_view);
        }

    }

    public ToDoRealmAdapter(Context context, RealmResults<ToDoItem> realmResults,
                            boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.todo_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder((FrameLayout) view);
        return viewHolder;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final ToDoItem toDoItem = realmResults.get(position);
        viewHolder.toDoItemTextView.setText(toDoItem.getDescription());
        viewHolder.itemView.setBackgroundColor(Colors.generateColor(toDoItem.getId()));
    }
}
