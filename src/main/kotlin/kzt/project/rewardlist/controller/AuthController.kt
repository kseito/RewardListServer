package kzt.project.rewardlist.controller

import kzt.project.rewardlist.NotFoundException
import kzt.project.rewardlist.UserRepository
import kzt.project.rewardlist.exception.AlreadyExistsException
import kzt.project.rewardlist.model.User
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth")
class AuthController(private val userRepository: UserRepository) {

    @PostMapping("sign_up")
    fun signUp(@RequestParam("todoist_id") todoistId: Long): User {
        return userRepository.create(todoistId)
    }

    @GetMapping("login")
    fun login(@RequestParam("todoist_id") todoistId: Long): User {
        return userRepository.findByTodoistId(todoistId) ?: throw NotFoundException()
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