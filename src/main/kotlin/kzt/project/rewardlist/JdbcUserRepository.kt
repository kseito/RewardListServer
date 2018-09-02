package kzt.project.rewardlist

import kzt.project.rewardlist.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JdbcUserRepository(private val jdbcTemplate: JdbcTemplate) : UserRepository {

    override fun create(todoistId: Long): User {
        jdbcTemplate.update("INSERT INTO user(todoist_id) VALUES(?)", todoistId)
        val id: Long = jdbcTemplate.queryForObject("SELECT last_insert_id()") ?: throw NotFoundException()
        return User(id, todoistId, 0)
    }

    override fun findById(user: User): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}