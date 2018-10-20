package kzt.project.rewardlist

import kzt.project.rewardlist.exception.AlreadyExistsException
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
        return userRepository.findById(todoistId) ?: throw NotFoundException()
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