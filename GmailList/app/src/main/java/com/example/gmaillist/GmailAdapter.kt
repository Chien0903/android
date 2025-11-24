package com.example.gmaillist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gmaillist.databinding.ItemGmailBinding

class GmailAdapter(private val gmailList: List<Gmail>) :
	RecyclerView.Adapter<GmailAdapter.GmailViewHolder>() {

	inner class GmailViewHolder(val binding: ItemGmailBinding) :
		RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GmailViewHolder {
		val binding = ItemGmailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return GmailViewHolder(binding)
	}

	override fun onBindViewHolder(holder: GmailViewHolder, position: Int) {
		val gmail = gmailList[position]
		with(holder.binding) {
			tvSender.text = gmail.sender
			tvTitle.text = gmail.title
			tvContent.text = gmail.content
			tvTime.text = gmail.time
			val initial = gmail.sender.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
			tvAvatar.text = initial

			// Assign specific colors based on sender name to match Gmail design
			val color = when (gmail.sender) {
				"Edurila.com" -> ContextCompat.getColor(root.context, R.color.gmail_blue)
				"Chris Abad" -> ContextCompat.getColor(root.context, R.color.gmail_orange)
				"Tuto.com" -> ContextCompat.getColor(root.context, R.color.gmail_green)
				"support" -> ContextCompat.getColor(root.context, R.color.gmail_gray)
				"Matt from Ionic" -> ContextCompat.getColor(root.context, R.color.gmail_light_green)
				else -> {
					// Fallback: use deterministic color from palette
					val palette = intArrayOf(
						ContextCompat.getColor(root.context, R.color.gmail_blue),
						ContextCompat.getColor(root.context, R.color.gmail_orange),
						ContextCompat.getColor(root.context, R.color.gmail_green),
						ContextCompat.getColor(root.context, R.color.gmail_gray),
						ContextCompat.getColor(root.context, R.color.gmail_light_green)
					)
					palette[Math.abs(gmail.sender.hashCode()) % palette.size]
				}
			}
			DrawableCompat.setTint(tvAvatar.background.mutate(), color)
		}
	}

	override fun getItemCount(): Int = gmailList.size
}
