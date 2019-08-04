package com.example.tododooo.dao;

//
// Created by Azmain Adel on 8/4/19.
//

import com.example.tododooo.model.ToDoItem;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ToDoItemDao {
    private Realm realm;

    public ToDoItemDao(Realm realm) {
        this.realm = realm;
    }

    private RealmQuery<ToDoItem> where(){
        return realm.where(ToDoItem.class);
    }

    public RealmResults<ToDoItem> findAllToDo(){
        return where()
                .findAllSorted("id", true);
    }

    public ToDoItem createToDo(){
        return realm.createObject(ToDoItem.class);
    }
}
