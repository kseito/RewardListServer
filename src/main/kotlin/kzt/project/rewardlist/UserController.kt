package kzt.project.rewardlist

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kzt.project.rewardlist.exception.AlreadyExistsException
import kzt.project.rewardlist.model.TodoistEvent
import kzt.project.rewardlist.model.TodoistWebhookResponse
import kzt.project.rewardlist.model.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController(private val userRepository: UserRepository) {

    @PostMapping("")
    fun create(@RequestParam("todoist_id") todoistId: Long): User {
        return userRepository.create(todoistId)
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AlreadyExistsException::class)
    @ResponseBody
    fun handleUserAlreadyExistError(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["status"] = HttpStatus.FORBIDDEN.value()
        map["message"] = "already exists"
        return map
    }

    @GetMapping("{todoist_id}")
    fun index(@PathVariable("todoist_id") todoistId: Long): User {
        return userRepository.findByTodoistId(todoistId) ?: throw NotFoundException()
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


    @PutMapping("{user_id}")
    fun updatePoint(@PathVariable("user_id") userId: Long, @RequestParam("additional_point") point: Int): User {
        return userRepository.updatePoint(userId, point)
    }

    @PostMapping("/receive")
    fun addPointFromWebhook(@RequestBody json: String) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(TodoistWebhookResponse::class.java)
        val todoistId = adapter.fromJson(json)?.eventData?.user_id ?: return
        userRepository.update(todoistId, 1)
    }
}