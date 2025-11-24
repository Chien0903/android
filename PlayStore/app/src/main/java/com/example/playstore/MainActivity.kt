package com.example.playstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val recyclerViewMain = findViewById<RecyclerView>(R.id.recyclerViewMain)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        
        // Tạo dữ liệu mẫu
        val sections = createSampleData()
        
        val adapter = AppSectionAdapter(sections)
        recyclerViewMain.adapter = adapter
    }
    
    private fun createSampleData(): List<AppSection> {
        return listOf(
            // Section "Suggested for you" (Sponsored) - Vertical layout
            AppSection(
                title = "Suggested for you",
                isSponsored = true,
                isHorizontal = false,
                apps = listOf(
                    App(
                        name = "Mech Assemble: Zombie Swarm",
                        genre = "Action • Role Playing • Roguelike • Zombie",
                        rating = 4.8f,
                        size = "624 MB"
                    ),
                    App(
                        name = "MU: Hồng Hoả Đao",
                        genre = "Role Playing",
                        rating = 4.8f,
                        size = "339 MB"
                    ),
                    App(
                        name = "War Inc: Rising",
                        genre = "Strategy • Tower defense",
                        rating = 4.9f,
                        size = "231 MB"
                    )
                )
            ),
            // Section "Recommended for you" - Horizontal layout
            AppSection(
                title = "Recommended for you",
                isSponsored = false,
                isHorizontal = true,
                apps = listOf(
                    App(
                        name = "Suno - AI Music &",
                        genre = "Music",
                        rating = 4.5f,
                        size = "50 MB"
                    ),
                    App(
                        name = "Claude by",
                        genre = "Productivity",
                        rating = 4.7f,
                        size = "30 MB"
                    ),
                    App(
                        name = "DramaBox -",
                        genre = "Entertainment",
                        rating = 4.6f,
                        size = "80 MB"
                    ),
                    App(
                        name = "Pil",
                        genre = "Social",
                        rating = 4.4f,
                        size = "45 MB"
                    ),
                    App(
                        name = "Spotify",
                        genre = "Music",
                        rating = 4.8f,
                        size = "100 MB"
                    ),
                    App(
                        name = "Instagram",
                        genre = "Social",
                        rating = 4.5f,
                        size = "150 MB"
                    )
                )
            ),
            // Thêm một số section khác để minh họa
            AppSection(
                title = "Top Free Games",
                isSponsored = false,
                isHorizontal = true,
                apps = listOf(
                    App(
                        name = "PUBG Mobile",
                        genre = "Action",
                        rating = 4.5f,
                        size = "2.5 GB"
                    ),
                    App(
                        name = "Free Fire",
                        genre = "Action",
                        rating = 4.6f,
                        size = "1.8 GB"
                    ),
                    App(
                        name = "Call of Duty",
                        genre = "Action",
                        rating = 4.7f,
                        size = "2.0 GB"
                    )
                )
            ),
            AppSection(
                title = "Trending Apps",
                isSponsored = false,
                isHorizontal = false,
                apps = listOf(
                    App(
                        name = "TikTok",
                        genre = "Social • Entertainment",
                        rating = 4.3f,
                        size = "200 MB"
                    ),
                    App(
                        name = "YouTube",
                        genre = "Video Players",
                        rating = 4.8f,
                        size = "150 MB"
                    ),
                    App(
                        name = "WhatsApp",
                        genre = "Communication",
                        rating = 4.6f,
                        size = "80 MB"
                    )
                )
            )
        )
    }
}

