package com.example.com.freetimes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by 59771 on 2017/9/14.
 */

public class DayItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final Dayseventadapter dayseventadapter;

    public DayItemTouchHelperCallback(Dayseventadapter adapter){
        dayseventadapter=adapter;
    }


    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        dayseventadapter.onItemDismiss(viewHolder.getAdapterPosition());
        Context context=viewHolder.itemView.getContext();
        Intent intent = new Intent(context, LongRunningService.class);
        intent.putExtra("isRepeat",true);
        context.startService(intent);
    }
}
