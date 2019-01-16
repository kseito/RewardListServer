package kzt.project.rewardlist

import kzt.project.rewardlist.model.User

interface UserRepository {

    fun create(todoistId: Long): User

    fun updatePointByTodoistId(todoistId: Long, additionalPoint: Int): User

    fun findByTodoistId(todoistId: Long): User?

    fun findById(userId: Long): User?

    fun updatePointById(userId: Long, point: Int): User
}