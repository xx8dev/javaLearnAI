# Практическое изучение Java

Персональный тренажёр с гибкой прогрессией: каждое задание закрепляет освоенные навыки и добавляет новые понятия из выбранной программы.

## Как работаем

1. Тренер создаёт условие, отдельные учебные примеры и JUnit-тесты.
2. Ученик самостоятельно проектирует и собирает проект с нуля, реализует решение и пишет: **«Решил»**.
3. Тренер проверяет код и запускает все тесты.
4. Успешная задача переносится в `tasks/completed/` вместе с решением и тестами.
5. Обновляются общий прогресс и подтверждённые навыки, затем выполняются commit и push.

Новые задания могут смешивать знания из разных разделов программы. После уверенного Java Core программа расширена на следующий этап backend-разработки.

## Формат задач: проектирование и сборка с нуля

Предпочтение ученика для текущей и следующих задач — самостоятельно создавать проект и весь код приложения.

- Ученик сам выбирает классы, методы, модели, роли компонентов и связи между ними в рамках требований задачи.
- Ученик вручную создаёт пакеты и исходные файлы, стартовый класс, конфигурацию приложения и SQL-схему, если они нужны.
- Ученик сам подключает зависимости, настраивает Maven, собирает и запускает приложение. Для отдельного нового проекта он также создаёт `pom.xml` и структуру каталогов; существующий учебный Maven-проект можно использовать как окружение проверки.
- Тренер предоставляет условие, внешний контракт, критерии готовности и автоматические тесты. Заготовки классов с `TODO`, готовая структура приложения и конфигурация решения не выдаются.
- Условие описывает необходимые роли и поведение. Имена внутренних классов и методов ученик выбирает сам; обязательные имена фиксируются только там, где без них нельзя определить внешний контракт или запустить проверку.
- Новые понятия объясняются на небольших работающих примерах из другого сценария. Эти примеры остаются в учебном материале; файлы решения ученик создаёт сам.

До создания приложения тесты могут завершаться ошибкой отсутствующей конфигурации или реализации. Это ожидаемое начальное состояние; задача засчитывается только после успешного запуска тестов и проверки ограничений.

## Границы курса

Темы обучения:

- типы данных, строки, числа, булевы значения, циклы и массивы;
- методы, область видимости, декомпозиция и правила написания кода;
- объекты, классы и коллекции;
- Git, GitHub, командная строка, JDK, отладка и IntelliJ IDEA;
- ООП: инкапсуляция, наследование, `Object`, статика, константы, перечисления, абстракция и полиморфизм;
- дженерики, unit-тесты, анализ проблем и итоговые проекты.
- современный Java: лямбды, Stream API, дата и время;
- SQL, PostgreSQL и проектирование реляционных данных; углублённые запросы, индексы, планы выполнения и конкурентные транзакции;
- JDBC и работа Java-приложения с базой данных;
- HTTP, REST API и Spring Boot;
- Spring JDBC, Spring Data JPA / Hibernate и транзакции;
- безопасность API, OAuth 2.0 / OpenID Connect и интеграционные тесты;
- Docker и Docker Compose: сборка образов и запуск приложения с PostgreSQL;
- CI/CD через GitHub Actions: проверки, публикация образов и развёртывание;
- самостоятельные backend-проекты от проектирования до запуска.

### Целевой стек Java backend junior

Это ориентир для следующих задач и итоговых проектов. Освоение каждого пункта подтверждается практическим решением; наличие технологии в плане само по себе не означает, что она уже освоена.

| Область | Технологии и навыки | Практический результат |
|---|---|---|
| Java | Java 21, ООП, коллекции, дженерики, исключения, Stream API, `java.time`; основы JVM, потоков и `ExecutorService` | Самостоятельно проектировать модель, обрабатывать ошибки и понимать базовые проблемы общего изменяемого состояния |
| Сборка и инструменты | Maven, Maven Wrapper, Git/GitHub, IntelliJ IDEA, отладчик, Linux и командная строка | Создавать проект, управлять зависимостями, собирать JAR, отлаживать код и работать с ветками |
| HTTP и API | HTTP/REST, JSON/Jackson, Spring Boot, Spring MVC, Bean Validation | Создавать CRUD API с DTO, фильтрацией, пагинацией, корректными статусами и единым форматом ошибок |
| SQL и база данных | PostgreSQL, проектирование и нормализация схемы, JOIN и агрегирование, подзапросы и EXISTS, CTE, оконные функции, составные и частичные индексы, `EXPLAIN (ANALYZE, BUFFERS)`, MVCC, уровни изоляции, блокировки и deadlock | Писать отчёты сложнее CRUD, читать планы выполнения, обоснованно выбирать индексы и проверять поведение конкурентных транзакций |
| Доступ к данным | JDBC / `JdbcTemplate`, Spring Data JPA, Hibernate, `@Transactional` | Связывать API с базой, моделировать отношения, управлять транзакциями и замечать проблему N+1 |
| Миграции | Flyway как основной инструмент; знакомство с Liquibase | Версионировать схему и разворачивать базу вместе с приложением |
| Безопасность | Spring Security, аутентификация и авторизация, роли, хеширование паролей, основы сессий и bearer-токенов | Ограничивать доступ к endpoint, различать `401` и `403`, хранить пароли корректно и использовать стандартные механизмы защиты |
| OAuth 2.0 и OpenID Connect | Роли client / authorization server / resource server, Authorization Code с PKCE, scopes, access / refresh / ID token; Spring Security OAuth2 Client и Resource Server, проверка JWT | Подключить вход через провайдера OpenID Connect и защитить отдельное API access-токеном, проверяя его подпись, issuer, audience, срок действия и права |
| Тестирование | JUnit 5, Mockito, MockMvc, Spring Boot Test, Testcontainers с PostgreSQL | Писать unit- и интеграционные тесты, проверять HTTP-контракт, SQL и откат транзакций |
| Документация API | OpenAPI/Swagger, `curl` или Postman, README проекта | Описывать запросы и ответы и давать воспроизводимые команды запуска и проверки |
| Docker и Compose | Dockerfile, сборка в несколько этапов, образы и контейнеры, registry и теги; `compose.yaml`, сети, volumes, healthcheck, зависимости сервисов, переменные окружения и Spring profiles | Самостоятельно собрать образ, поднять API с PostgreSQL одной командой, сохранить данные при пересоздании контейнеров и диагностировать запуск через логи |
| Диагностика | SLF4J/Logback, уровни логирования, Spring Boot Actuator | Читать логи, находить причины ошибок и проверять состояние приложения через health endpoint |
| CI/CD | GitHub Actions: сборка, unit- и интеграционные тесты с PostgreSQL, артефакты, публикация Docker-образа, environments и secrets, развёртывание на Linux через Compose, проверка health и откат версии | Построить путь от коммита до работающего тестового окружения: проверить код, опубликовать версию, развернуть её, проверить запуск и вернуть предыдущую версию при неудаче |

Docker и Compose, углублённый SQL, CI/CD и OAuth 2.0 / OpenID Connect входят в основную программу. Это будущие учебные блоки; подтверждённые навыки добавляются только после успешных практических задач.

Порядок backend-практики: Spring MVC и валидация → Spring JDBC и PostgreSQL → JPA/Hibernate и транзакции → миграции → Spring Security → Docker и Compose, Testcontainers → OAuth 2.0 / OpenID Connect → CI/CD и развёртывание → итоговый проект. Углублённый SQL возвращается отдельными задачами после освоения доступа к данным. Порядок гибкий; знакомые инструменты тестирования, Git и Maven используются на каждом этапе.

### Практические границы новых блоков

- **Docker и Compose:** ручное создание Dockerfile и `compose.yaml`; API и PostgreSQL в отдельных контейнерах; адреса сервисов внутри сети; хранение базы в volume; ожидание готовности PostgreSQL; внешняя конфигурация, просмотр логов и обновление образа.
- **SQL глубже базового уровня:** отчёты через JOIN, подзапросы, CTE и оконные функции; разбор `EXPLAIN (ANALYZE, BUFFERS)`; сравнение запросов до и после индекса; изоляция транзакций, блокировки строк и воспроизводимые ситуации конкурентного изменения данных.
- **CI/CD:** CI собирает и проверяет изменение, CD доставляет проверенную версию в окружение. Практика включает интеграционные тесты с PostgreSQL, публикацию образа с конкретным тегом, развёртывание на учебном Linux-сервере, применение миграций, проверку health и откат приложения. Откат образа и откат схемы базы рассматриваются отдельно; миграции не считаются автоматически обратимыми.
- **OAuth 2.0 / OpenID Connect:** сначала назначение протоколов и путь запроса, затем Authorization Code с PKCE, вход через провайдера и Resource Server. Различаем выдачу прав доступа через OAuth 2.0 и аутентификацию через OpenID Connect, access token для API и ID token для клиента. Учимся использовать стандартные компоненты Spring Security и локального учебного провайдера; собственный OAuth-сервер и криптографию не пишем.

Готовность к итоговому проекту: ученик с нуля создаёт REST API с PostgreSQL, миграциями, ролями доступа, тестами и документацией; умеет реализовать сложный SQL-отчёт и объяснить план запроса, подключить OpenID Connect / OAuth 2.0, собрать приложение через Docker Compose и настроить CI/CD до учебного окружения; объясняет архитектуру, диагностирует ошибки и выполняет откат версии приложения.

Дополнительные темы после основного стека: Redis, Kafka/RabbitMQ, Gradle, углублённая многопоточность и микросервисы. Их вводим по потребности проекта. Kubernetes и сложная распределённая инфраструктура остаются за границами базового курса.

Материал для практики: [Spring Boot и SQL/JPA](https://docs.spring.io/spring-boot/3.5/reference/data/sql.html), [миграции и инициализация базы](https://docs.spring.io/spring-boot/3.5/how-to/data-initialization.html), [Spring Security](https://docs.spring.io/spring-security/reference/servlet/index.html), [Testcontainers в Spring Boot](https://docs.spring.io/spring-boot/3.5/reference/testing/testcontainers.html), [модель приложения Docker Compose](https://docs.docker.com/compose/intro/compose-application-model/), [оконные функции PostgreSQL](https://www.postgresql.org/docs/current/tutorial-window.html), [OAuth 2.0 в Spring Security](https://docs.spring.io/spring-security/reference/6.5/servlet/oauth2/index.html), [развёртывание через GitHub Actions](https://docs.github.com/en/actions/how-tos/deploy/configure-and-manage-deployments/control-deployments).

## Команды

- `Решил` / `Проверь задание` — проверить, архивировать и отправить успешное решение.
- `Ещё задание` / `Следующая задача` — получить следующий шаг прогрессии.
- `Сложнее` — получить задачу с большей самостоятельностью и новыми понятиями.
- `Подсказка`, `Упрости`, `Объясни ...` — получить помощь без готового решения.
- `Покажи прогресс` — увидеть общий счётчик, навыки и текущую задачу.

## Проверка

Windows:

```shell
.\mvnw.cmd test
```

Linux/macOS:

```shell
./mvnw test
```

В текущем Linux-окружении установленный полный JDK 21 и локальный Maven-кеш задаются явно; исходный код компилируется для Java 21:

```shell
JAVA_HOME=/home/kriksson/.jdks/temurin-21.0.12.1 PATH=/home/kriksson/.jdks/temurin-21.0.12.1/bin:$PATH MAVEN_USER_HOME="$PWD/.maven-home" bash ./mvnw -Dmaven.repo.local="$PWD/.maven-home/repository" clean test
```

Для запуска из IntelliJ IDEA выбери этот JDK 21 в Project SDK и настройках запуска. Spring Boot 3.5.16 поддерживает Java 17–25; прежняя рекомендация использовать JDK 27 исправлена. [Требования Spring Boot](https://docs.spring.io/spring-boot/3.5/system-requirements.html).

## Профиль ученика

- Имя: Анастас.
- Опыт Java: отсутствует.
- Интересы: финансовые операции, игры и кино.
- Текущий практический уровень: успешно применяет `int`, арифметику, методы с параметрами, `boolean`, сравнения, `if`, `while` и массивы `int[]`; изучает вложенные циклы `while`.
- Решено задач: **4**.
- Подтверждённые навыки: тип `int`, целочисленные операции, методы с параметрами и возвращаемым значением, логический тип `boolean`, сравнение чисел, ветвление `if`, цикл `while`, массивы `int[]`, индексация и обход массива.
- Подтверждённый стек: Java 21; код компилируется с `--release 21`.

[![Java 21](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/) [![int arithmetic](https://img.shields.io/badge/Java-int_arithmetic-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Integer.html) [![Methods](https://img.shields.io/badge/Java-methods_with_parameters-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/reflect/Method.html) [![boolean](https://img.shields.io/badge/Java-boolean-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Boolean.html) [![Comparisons](https://img.shields.io/badge/Java-numeric_comparisons-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://docs.oracle.com/en/java/javase/21/language/java-language-changes.html) [![if](https://img.shields.io/badge/Java-if_conditions-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html) [![while](https://img.shields.io/badge/Java-while_loop-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html) [![Arrays](https://img.shields.io/badge/Java-arrays-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html)

Навыки и стек будут добавляться только после успешных задач, в которых ученик применит их на практике.

## Прогресс

- Решено задач: **4**.
- Последняя решённая задача: [№4 — Какая выручка была за кинопоказы?](tasks/completed/task_004_cinema_ticket_revenue/README.md).
- История: [№1 — Первое пополнение игрового счёта](tasks/completed/task_001_game_balance_top_up/README.md), [№2 — Достаточно ли средств для покупки?](tasks/completed/task_002_game_purchase_affordability/README.md), [№3 — Сколько игровых дней копить на предмет?](tasks/completed/task_003_game_savings_days/README.md), [№4 — Какая выручка была за кинопоказы?](tasks/completed/task_004_cinema_ticket_revenue/README.md).

## Последняя решённая задача

[№4 — Какая выручка была за кинопоказы?](tasks/completed/task_004_cinema_ticket_revenue/README.md).

## Текущая задача

[№5 — Сколько заработал кинотеатр при полной посадке?](tasks/active/task_005_full_cinema_revenue/README.md).

## Структура

```text
tasks/
  active/       текущее задание
  completed/    выполненные задания с решениями и тестами
src/main/java/  рабочие файлы текущего задания
src/test/java/  тесты текущего задания
```
