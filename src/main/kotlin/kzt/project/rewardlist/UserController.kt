package kzt.project.rewardlist

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kzt.project.rewardlist.exception.AlreadyExistsException
import kzt.project.rewardlist.model.RewardPoint
import kzt.project.rewardlist.model.TodoistWebhookResponse
import kzt.project.rewardlist.model.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController(private val userRepository: UserRepository) {

    @GetMapping("{user_id}")
    fun getPoint(@PathVariable("user_id") userId: Long): RewardPoint {
        val user = userRepository.findById(userId) ?: throw NotFoundException()
        return RewardPoint(user.point)
    }

    @PutMapping("{user_id}")
    fun updatePoint(@PathVariable("user_id") userId: Long, @RequestParam("additional_point") point: Int): User {
        return userRepository.updatePointById(userId, point)
    }

    @PostMapping("receive")
    fun addPointFromWebhook(@RequestBody json: String): User {
        println("Received Json is $json")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(TodoistWebhookResponse::class.java)
        val todoistResponse = adapter.fromJson(json) ?: throw NotFoundException()
        val todoistId = todoistResponse.eventData.userId
        val priority = todoistResponse.eventData.priority
        val point = convertPriorityToPoint(priority)
        return userRepository.updatePointByTodoistId(todoistId, point)
    }

    private fun convertPriorityToPoint(priority: Int): Int {
        return when(priority) {
            4 -> 8
            3 -> 5
            2 -> 3
            else -> 1
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    fun handleUserNotFoundError(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["status"] = HttpStatus.BAD_REQUEST.value()
        map["message"] = "user not found"
        return map
    }
}