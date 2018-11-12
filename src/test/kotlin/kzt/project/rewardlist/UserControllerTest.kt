package kzt.project.rewardlist

import com.squareup.moshi.Moshi
import kzt.project.rewardlist.model.User
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var commandLineRunner: CommandLineRunner

    @Test
    fun canCreateSpecifiedTodoistId() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .param("todoist_id", "123456"))

        Mockito.verify(userRepository).create(123456)
    }

    @Test
    fun ifFoundUser_shouldReturnUser() {
        val user = User(123456, 1, 0)
        Mockito.`when`(userRepository.findByTodoistId(123456)).thenReturn(user)

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/123456"))
                .andExpect(status().isOk)
                .andExpect(content().json(adapter.toJson(user)))
    }

    @Test
    fun ifNotFoundUser_shouldThrowException() {
        Mockito.`when`(userRepository.findByTodoistId(123456)).thenReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/123456"))
                .andExpect(content().string(Matchers.containsString("user not found")))
    }

}