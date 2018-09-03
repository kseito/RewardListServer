package kzt.project.rewardlist

import kzt.project.rewardlist.exception.AlreadyExistsException
import kzt.project.rewardlist.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.thymeleaf.exceptions.AlreadyInitializedException

@Repository
class JdbcUserRepository(private val jdbcTemplate: JdbcTemplate) : UserRepository {

    private val rowMapper = RowMapper { rs, _ ->
        User(rs.getLong("id"), rs.getLong("todoist_id"), rs.getInt("point"))
    }

    override fun create(todoistId: Long): User {
        val user = jdbcTemplate.query("SELECT id, todoist_id, point FROM user WHERE todoist_id = ?", rowMapper, todoistId).firstOrNull()
        if (user != null) {
            throw AlreadyExistsException()
        }
        jdbcTemplate.update("INSERT INTO user(todoist_id) VALUES(?)", todoistId)
        val id: Long = jdbcTemplate.queryForObject("SELECT last_insert_id()") ?: throw NotFoundException()
        return User(id, todoistId, 0)
    }

    override fun findById(user: User): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}