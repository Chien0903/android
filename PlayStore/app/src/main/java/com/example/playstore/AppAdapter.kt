package com.example.playstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(
    private val apps: List<App>,
    private val isHorizontal: Boolean
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appIcon: ImageView = itemView.findViewById(R.id.appIcon)
        val appName: TextView = itemView.findViewById(R.id.appName)
        val appGenre: TextView? = itemView.findViewById(R.id.appGenre)
        val appRating: TextView? = itemView.findViewById(R.id.appRating)
        val appSize: TextView? = itemView.findViewById(R.id.appSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val layoutId = if (isHorizontal) {
            R.layout.item_app_horizontal
        } else {
            R.layout.item_app_vertical
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.appIcon.setImageResource(app.iconResId)
        holder.appName.text = app.name
        
        if (isHorizontal) {
            // Horizontal layout chỉ hiển thị tên
        } else {
            // Vertical layout hiển thị đầy đủ thông tin
            holder.appGenre?.text = app.genre
            holder.appRating?.text = app.rating.toString()
            holder.appSize?.text = app.size
        }
    }

    override fun getItemCount(): Int = apps.size
}

