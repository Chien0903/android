package com.example.playstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppSectionAdapter(
    private val sections: List<AppSection>
) : RecyclerView.Adapter<AppSectionAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionTitle: TextView = itemView.findViewById(R.id.sectionTitle)
        val sectionMoreIcon: ImageView = itemView.findViewById(R.id.sectionMoreIcon)
        val sectionArrowIcon: FrameLayout = itemView.findViewById(R.id.sectionArrowIcon)
        val recyclerViewApps: RecyclerView = itemView.findViewById(R.id.recyclerViewApps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sections[position]
        
        // Hiển thị "Sponsored • " nếu là section quảng cáo
        if (section.isSponsored) {
            holder.sectionTitle.text = "Sponsored • ${section.title}"
            holder.sectionMoreIcon.visibility = View.VISIBLE
            holder.sectionArrowIcon.visibility = View.GONE
        } else {
            holder.sectionTitle.text = section.title
            holder.sectionMoreIcon.visibility = View.GONE
            holder.sectionArrowIcon.visibility = View.VISIBLE
        }
        
        // Setup RecyclerView cho apps - tạo layout manager mới mỗi lần
        val layoutManager = if (section.isHorizontal) {
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        } else {
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        }
        
        // Đặt lại layout manager và adapter
        holder.recyclerViewApps.layoutManager = layoutManager
        holder.recyclerViewApps.setHasFixedSize(false)
        holder.recyclerViewApps.isNestedScrollingEnabled = false
        
        // Tạo adapter mới với dữ liệu
        val appAdapter = AppAdapter(section.apps, section.isHorizontal)
        holder.recyclerViewApps.adapter = appAdapter
        
        // Đảm bảo RecyclerView con được đo lại
        holder.recyclerViewApps.post {
            holder.recyclerViewApps.requestLayout()
            holder.itemView.requestLayout()
        }
    }

    override fun getItemCount(): Int = sections.size
}

