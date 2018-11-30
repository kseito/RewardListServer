package kzt.project.rewardlist

import kzt.project.rewardlist.model.User

interface UserRepository {

    fun create(todoistId: Long): User

    fun update(todoistId: Long, additionalPoint: Int): User

    fun findByTodoistId(todoistId: Long): User?

    fun updatePoint(userId: Long, point: Int): User
}