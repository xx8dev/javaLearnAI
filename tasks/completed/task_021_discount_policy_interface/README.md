# Задача №21 — Как касса применяет разные правила скидки?

**Сложность:** начальная, знакомство с интерфейсом и полиморфным вызовом.

**Закрепляем:** классы, конструкторы, инкапсуляцию, проверку входных данных, переопределение методов.

**Новое понятие:** интерфейс — это общий контракт, который могут выполнять разные классы. Код, которому нужен этот контракт, может работать с любым подходящим классом.

## Зачем это нужно

В кассе могут действовать разные акции. Код расчёта заказа должен попросить выбранное правило рассчитать скидку, не выясняя, какой именно класс правила перед ним. Так правила можно менять отдельно от вызывающего кода.

## Учебный пример: команды музыкального плеера

В примере команды «пауза» и «запуск» отвечают одному интерфейсу, но по-разному обрабатывают название композиции. Эти файлы — только учебный пример; решение задачи создавай отдельно.

`PlaybackCommand.java`:

```java
public interface PlaybackCommand {
    String execute(String trackTitle);
}
```

`PauseCommand.java`:

```java
public final class PauseCommand implements PlaybackCommand {
    @Override
    public String execute(String trackTitle) {
        return "Пауза: " + trackTitle;
    }
}
```

`StartCommand.java`:

```java
public final class StartCommand implements PlaybackCommand {
    @Override
    public String execute(String trackTitle) {
        return "Запуск: " + trackTitle;
    }
}
```

`PlaybackDemo.java`:

```java
public class PlaybackDemo {
    public static void show(PlaybackCommand command, String trackTitle) {
        System.out.println(command.execute(trackTitle));
    }

    public static void main(String[] args) {
        PlaybackCommand command = new PauseCommand();
        show(command, "Night Drive");
        show(new StartCommand(), "Night Drive");
    }
}
```

- Интерфейс задаёт метод `execute`, который должен предоставить каждый класс-команда.
- `implements PlaybackCommand` означает, что класс принимает этот контракт; `@Override` отмечает реализацию метода.
- Переменная `command` имеет тип интерфейса, а объект за ней — `PauseCommand`. Во время вызова Java выбирает реализацию объекта.
- `show` получает команду и название композиции, передаёт название в `execute` и печатает результат. На экран выводятся `Пауза: Night Drive` и `Запуск: Night Drive`.

## Условие

Создай в пакете `learning.task021` публичный интерфейс `PurchaseDiscountPolicy` и два публичных класса, которые его реализуют:

- `NoDiscountPolicy` — скидки нет;
- `TenPercentDiscountPolicy` — скидка составляет 10% от цены.

Контракт интерфейса:

```java
int calculateDiscountRubles(int priceRubles);
```

Цена передаётся целым числом рублей. Допустимый диапазон: от `0` до `1_000_000` включительно. Обе реализации должны выбрасывать `IllegalArgumentException`, если цена выходит за диапазон. Для десятипроцентной скидки дробную часть отбрасывай целочисленным делением: например, скидка от 99 рублей равна 9 рублям.

Вызывать правила должно быть возможно через переменную типа `PurchaseDiscountPolicy`, не проверяя класс объекта через `if` или `instanceof`.

## Примеры

| Правило | Цена | Скидка |
|---|---:|---:|
| Без скидки | 1 250 | 0 |
| 10% | 1 250 | 125 |
| 10% | 99 | 9 |

## Ограничения и готовность

- Не изменяй переданную цену и не сохраняй её как состояние объекта.
- Не округляй скидку вверх.
- Создай каждый публичный тип в отдельном `.java`-файле.
- Проверки ошибок и расчёта должны работать одинаково при вызове через интерфейс и через класс реализации.
- Вызови обе реализации через переменные типа `PurchaseDiscountPolicy`.

## Где создавать файлы

- Твои три файла: `src/main/java/learning/task021/`.
- Автоматические проверки: `src/test/java/learning/task021/Task021DiscountPolicyInterfaceTest.java` в корне проекта.
- Условие и пример находятся в этом `README.md`; пример не копируй в файлы решения.

Пока реализации нет, тесты могут завершиться ошибкой компиляции — это ожидаемо. Когда создашь классы, запусти из корня проекта:

```powershell
.\mvnw.cmd test
```
