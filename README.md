# BaseApp

Базовый преднастроенный шаблон Android-приложения.

## Технологии
В приложение включены следующие компоненты:
* Dagger2
* MVVM
* Navigation
* Retrofit + OkHttp
* Coroutines
* Room
* Timber Logging
* Stetho
* Firebase Analytics
* Firebase Crashlytics

## Основная информация

### Архитектура
В проекте используется структура package-by-feature.
Архитектура приложения MVVM.

Взаимодействие между компонентами предполагается следующее: 
`View - Databinding - ViewModel - UseCase - Repository - (Database and RemoteAPI)`

### Разрешения
Разрешения в приложении имеют принудительный характер. 
В случае отсутствия необходимого разрешения вызывается незакрываемое диалоговое окно с просьбой предоставить недостающие разрешения `MainActivity.showPermissionsDialog()`.
Разрешения проверяются в `MainActivity.onStart()`, таким образом, если пользователь свернет приложение и отзовет разрешения, приложение заново запросит их при его открытии.

### Информационные сообщения
В приложении используется глобальный менеджер отображения информации для пользователя - `InfoHandler`.
В нем имеются `LiveData` подписки, на события которых подписывается `MainActivity` и вызвает методы их отображения.
Подписки:
* infoMessageEvent - сообщение информационного характера
* errorMessageEvent - сообщение об ошибке
* isLoading - статус прогресс-бара загрузки

### Навигация
Для навигации внутри приложения используется класс `Navigator`.
MainActivity подписывается на события навигации и вызвает методы у `NavController` в зависимости от переданного `NavCommand`.

### Работа с данными
Для соблюдения инкапсуляции при работе с данными во `ViewModel` рекомендуется инжектить не репозитории, а `UseCase` определенных методов необходимых репозиториев.
Асинхронные запросы в приложении реализованы при помощи suspend-функций Kotlin coroutines.

## Настройка приложения

Для минимальной настройки нового проекта на основе базового приложения необходимо:
* заменить имя пакета во всем приложении
* зарегистрировать приложение в FireBase Crashlytics и заменить файл google-services.json
* заменить ссылки, имена приложения и applicationId в файле .gitlab-ci.yml
* настроить адреса сервера в файле build.gradle

