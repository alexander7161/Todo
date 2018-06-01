package kcltech.kcltechtodo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.task_list_item.view.*

class TListAdapter(items : ArrayList<TaskData>, context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private var listTasks: List<TaskData> = items
    private var context : Context = context

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return listTasks.size
    }

    fun addTasks(listTasks: List<TaskData>) {
        this.listTasks = listTasks
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.title?.text = listTasks.get(position).title
        holder?.dueDate?.text = listTasks.get(position).dueDate.toString("d MMM YYYY")
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val title = view.title_view
    val dueDate = view.due_date
}