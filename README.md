# Цифровой учет книг в библиотеке

## Задача

В местной библиотеке хотят перейти на цифровой учет книг. Вам необходимо реализовать веб-приложение для них. Библиотекари должны иметь возможность регистрировать читателей, выдавать им книги и освобождать книги (после того, как читатель возвращает книгу обратно в библиотеку). Должна быть настроена аутентификация и авторизация пользователей. Кроме того должна быть настроена защита от вредоносный запросов

## Сущности

В проекте используются две основные сущности:

### Человек (Person)

- Поля: ФИО (UNIQUE), год рождения

### Книга (Book)

- Поля: название, автор, год

Отношение между сущностями: Один ко Многим.

У каждого человека может быть множество книг, а каждая книга может принадлежать только одному человеку.

## База данных

Для этого проекта создайте новую базу данных с названием "project1". В этой базе данных необходимо создать две таблицы - "Person" и "Book". Для всех таблиц настройте автоматическую генерацию идентификаторов (id).

## Функционал приложения

Необходимый функционал, который должно предоставлять веб-приложение:

## Защита
1. Так как приложение монолитное, была настроена CSRF защита

### Аутентификация
1. Аутентификация и регистрация пользователя.
2. Изменения пароля пользователя, если забыли пароль от аккаунта

### Авторизация
1. У каждой роли свои страницы пользования
2. Пользователь может:
   1. Просматривать и освобождать использованные книги.
   2. Просматривать свои личные данные, а также преобретенные книги.
   3. Редактировать логин, дату рождения в формате (mm.dd.yyyy).
   4. Редактировать пароль.
   5. Удалять свой аккаунт.
   6. Выходить с аккаунта.
3. Адмиинистратор может:
   1. Просматривать список, редактировать, блокировать, активировать, и удалять аккаунты.
   2. Просматривать список, редактировать, и удалять книги.
   3. Искать книги

### Изменение роли
1. Самый первый пользователь и администратор, может изменить свою роль после подтверждения секретного пароля
2. Другие пользователи не будут иметь доступ к этому сайту

## Другие функциональности
1. Страницы добавления, изменения и удаления человека.
2. Страницы добавления, изменения и удаления книги.
3. Страница со списком всех людей, где их имена являются ссылками на страницы человека.
4. Страница со списком всех книг, где названия книг являются ссылками на страницы книги.
5. Страница человека, на которой отображаются его данные и список взятых книг. Если человек не взял ни одной книги, вместо списка должен быть текст "Человек пока не взял ни одной книги".
6. Страница книги, на которой отображаются данные о книге и имя человека, который взял эту книгу. Если книга не была взята никем, должен отображаться текст "Эта книга свободна".
7. На странице книги, если книга взята человеком, рядом с его именем должна быть кнопка "Освободить книгу". По нажатию на эту кнопку книга становится доступной для выдачи другому читателю и удаляется из списка книг у данного человека.
8. На странице книги, если книга свободна, должен быть выпадающий список со всеми людьми и кнопка "Назначить книгу". По нажатию на эту кнопку, выбранная книга назначается выбранному человеку и появляется в его списке книг.
9. Все поля должны проходить валидацию с использованием аннотаций @Valid и Spring Validator, если это необходимо.
