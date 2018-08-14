package kzt.project.rewardlist

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tasks")
class TaskController {

    @GetMapping("")
    fun index(model: Model): String {
        val tasks = listOf(
                Task(1, "英単語を覚える", false),
                Task(2, "晩御飯の食材を買う", false)
        )
        return tasks.toString()
    }
}