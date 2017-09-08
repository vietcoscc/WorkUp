package com.example.viet.workup.event;

import android.view.View;

import com.example.viet.workup.model.Board;

import java.util.ArrayList;

/**
 * Created by viet on 07/09/2017.
 */

public class Listener {
    public interface OnItemLongClickListener {
        void onLongClick(View view, int position);
    }

    public interface OnItemClickListenter {
        void onClick(View view, int position);
    }

}
