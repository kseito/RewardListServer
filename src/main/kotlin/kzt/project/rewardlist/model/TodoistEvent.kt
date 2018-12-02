package kzt.project.rewardlist.model

import com.squareup.moshi.Json

data class TodoistEvent(@Json(name = "user_id")val userId: Long, val priority: Int)