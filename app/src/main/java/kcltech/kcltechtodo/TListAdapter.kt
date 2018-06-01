package kcltech.kcltechtodo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.task_list_item.view.*

class TListAdapter(items : ArrayList<TaskData>, listener: OnItemClickListener) : RecyclerView.Adapter<ViewHolder>() {

    private var listTasks: List<TaskData> = items
    private var listenerTask: OnItemClickListener = listener

    // Gets the number of tasks in the list
    override fun getItemCount(): Int {
        return listTasks.size
    }

    // update the task list.
    fun addTasks(listTasks: List<TaskData>) {
        this.listTasks = listTasks
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.task_list_item, parent, false))
    }

    // Binds each task in the ArrayList to a view and listener
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var currentTask: TaskData = listTasks[position]

        holder?.title?.text = currentTask.title
        holder?.dueDate?.text = currentTask.dueDate.toString("d MMM YYYY")
        holder!!.bind(currentTask, listenerTask)
    }

    interface OnItemClickListener {
        fun onItemClick(task: TaskData)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.title_view
    val dueDate = view.due_date

    fun bind(task: TaskData, listener: TListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            listener.onItemClick(task)
        }
    }

}