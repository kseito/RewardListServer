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

    @Test
    fun shouldCreateNewUser() {
        val user = repository.create(1)
        assertThat(user).isNotNull()
        assertThat(user.todoistId).isEqualTo(1)
        assertThat(user.point).isEqualTo(0)
    }

    @Test
    fun shouldFindCreatedUser() {
        val createdUser = repository.create(1)
        val foundUser = repository.findById(createdUser.id)

        assertThat(foundUser).isEqualTo(createdUser)
    }
}