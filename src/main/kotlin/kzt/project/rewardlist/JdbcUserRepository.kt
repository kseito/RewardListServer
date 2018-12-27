package kzt.project.rewardlist

import kzt.project.rewardlist.exception.AlreadyExistsException
import kzt.project.rewardlist.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcUserRepository(private val jdbcTemplate: JdbcTemplate) : UserRepository {

    private val rowMapper = RowMapper { rs, _ ->
        User(rs.getLong("id"), rs.getLong("todoist_id"), rs.getInt("point"))
    }

    override fun create(todoistId: Long): User {
        val user = findByTodoistId(todoistId)
        if (user != null) {
            throw AlreadyExistsException()
        }
        jdbcTemplate.update("INSERT INTO test_schema.user(todoist_id) VALUES(?)", todoistId)
        val id: Long = jdbcTemplate.queryForObject("SELECT lastval()") ?: throw NotFoundException()
        return User(id, todoistId, 0)
    }

    override fun updatePointByTodoistId(todoistId: Long, additionalPoint: Int): User {
        val user = findByTodoistId(todoistId) ?: throw NotFoundException()
        val newPoint = user.point + additionalPoint
        jdbcTemplate.update("UPDATE test_schema.user SET point = $newPoint WHERE todoist_id = ?", todoistId)
        return User(user.id, user.todoistId, newPoint)
    }

    override fun findByTodoistId(todoistId: Long): User? =
            jdbcTemplate.query("SELECT id, todoist_id, point FROM test_schema.user WHERE todoist_id = ?", rowMapper, todoistId).firstOrNull()

    override fun updatePointById(userId: Long, point: Int): User {
        val user = findById(userId) ?: throw NotFoundException()
        val newPoint = user.point + point
        jdbcTemplate.update("UPDATE test_schema.user SET point = $newPoint WHERE id = ?", userId)
        return User(user.id, user.todoistId, newPoint)
    }

    private fun findById(userId: Long): User? =
            jdbcTemplate.query("SELECT id, todoist_id, point FROM test_schema.user WHERE id = ?", rowMapper, userId).firstOrNull()


}