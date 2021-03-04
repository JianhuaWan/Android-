package consultan.vanke.com.listener

import android.view.View
import android.view.ViewGroup

interface OnItemClickListener<T> {
    fun onItemClick(parent: ViewGroup?, view: View?, t: T, position: Int)
    fun onItemLongClick(parent: ViewGroup?, view: View?, t: T, position: Int): Boolean
}