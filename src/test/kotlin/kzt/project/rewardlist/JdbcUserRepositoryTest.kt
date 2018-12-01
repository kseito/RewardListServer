package kzt.project.rewardlist

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@Sql(statements = arrayOf("DELETE FROM user"))
class JdbcUserRepositoryTest {

    @Autowired
    private lateinit var repository: JdbcUserRepository

    private val todoistId = 1L

    @Test
    fun shouldCreateNewUser() {
        val user = repository.create(1)
        assertThat(user).isNotNull()
        assertThat(user.todoistId).isEqualTo(1)
        assertThat(user.point).isEqualTo(0)
    }

    @Test
    fun shouldFindCreatedUser() {
        val createdUser = repository.create(todoistId)
        val foundUser = repository.findByTodoistId(todoistId)

        assertThat(foundUser).isEqualTo(createdUser)
    }

    @Test
    fun shouldUpdateCreatedUser() {
        repository.create(todoistId)
        val createdUser = repository.findByTodoistId(todoistId)
        repository.updatePointById(createdUser!!.id, 12)
        var actual = repository.findByTodoistId(todoistId)

        assertThat(actual!!.point).isEqualTo(12)

        repository.updatePointById(createdUser.id, -5)
        actual = repository.findByTodoistId(todoistId)

        assertThat(actual!!.point).isEqualTo(7)
    }
}