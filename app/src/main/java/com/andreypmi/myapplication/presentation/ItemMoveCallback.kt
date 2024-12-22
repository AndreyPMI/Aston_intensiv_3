package com.andreypmi.myapplication.presentation

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.andreypmi.myapplication.domain.ContactAdapter

class ItemMoveCallback(
    private val adapter: RecyclerView.Adapter<*>,
    private val onMove: (Int, Int) -> Unit
) : ItemTouchHelper.Callback() {

    private var targetPosition: Int = -1
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        targetPosition = target.adapterPosition

        adapter.notifyItemMoved(fromPosition, targetPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder?.itemView?.alpha = 0.5f
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = 1.0f
        if(targetPosition != -1) {
            onMove(viewHolder.adapterPosition, targetPosition)
            targetPosition = -1
        }
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Not used in our case, but it's required by the interface
    }
}