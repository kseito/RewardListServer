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
    fun handleError(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["status"] = HttpStatus.FORBIDDEN.value()
        map["message"] = "already exists"
        return map
    }

    @GetMapping("{id}")
    fun index(@PathVariable("id") id: Long): User {
        return userRepository.findById(id) ?: throw NotFoundException()
    }

}