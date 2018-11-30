package kzt.project.rewardlist.model

import com.squareup.moshi.Json

data class TodoistWebhookResponse(@Json(name = "event_data") val eventData: TodoistEvent)