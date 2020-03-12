package com.tadiuzzz.baseapp.data.entity

import androidx.room.Entity

/**
 * Пример бизнес объекта приложения.
 * Для изменения десериализуемого имени поля добавить перед ним @SerializedName("имя_поля_в_json").
 * Для исключения поля при сериализации (чтобы не отправлять на сервер) добавить перед ним @Expose(serialize = false).
 * Для изменения имени поля в базе данных добавить перед ним  @ColumnInfo(name = "имя_поля_в_базе").
 * Для исключения добавления в базу данных поля/вложенного объекта добавить перед ним @Ignore
 *
 *
 * @property id пример поля id
 * @property name пример поля name
 * @property value пример поля value
 */
@Entity(primaryKeys = ["id"])
class EntityExample(
    val id: Int,
    val name: String,
    val value: Int
)