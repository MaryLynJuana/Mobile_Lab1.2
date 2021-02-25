package ua.kpi.comsys.ip8313.lab1_2

fun main() {
    // Дано рядок у форматі "Student1 - Group1; Student2 - Group2; ..."

    val studentsStr = "Дмитренко Олександр - ІП-84; Матвійчук Андрій - ІВ-83; Лесик Сергій - ІО-82; Ткаченко Ярослав - ІВ-83; Аверкова Анастасія - ІО-83; Соловйов Даніїл - ІО-83; Рахуба Вероніка - ІО-81; Кочерук Давид - ІВ-83; Лихацька Юлія - ІВ-82; Головенець Руслан - ІВ-83; Ющенко Андрій - ІО-82; Мінченко Володимир - ІП-83; Мартинюк Назар - ІО-82; Базова Лідія - ІВ-81; Снігурець Олег - ІВ-81; Роман Олександр - ІО-82; Дудка Максим - ІО-81; Кулініч Віталій - ІВ-81; Жуков Михайло - ІП-83; Грабко Михайло - ІВ-81; Іванов Володимир - ІО-81; Востриков Нікіта - ІО-82; Бондаренко Максим - ІВ-83; Скрипченко Володимир - ІВ-82; Кобук Назар - ІО-81; Дровнін Павло - ІВ-83; Тарасенко Юлія - ІО-82; Дрозд Світлана - ІВ-81; Фещенко Кирил - ІО-82; Крамар Віктор - ІО-83; Іванов Дмитро - ІВ-82"

    // Завдання 1
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – відсортований масив студентів, які відносяться до відповідної групи

    val studentsGroups = mutableMapOf<String, List<String>>()

    // Ваш код починається тут

    val studentsList = studentsStr.split("; ")
    studentsList.forEach {
        val (name, group) = it.split(" - ")
        val groupStudentsList = (studentsGroups[ group ]?: mutableListOf<String>()) + name
        studentsGroups[ group ] = groupStudentsList.sorted()
    }

    // Ваш код закінчується тут

    println("Завдання 1")
    println(studentsGroups)

    // Дано масив з максимально можливими оцінками

    val points = listOf<Int>(12, 12, 12, 12, 12, 12, 12, 16)

    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – словник, де:
    //   - ключ – студент, який відносяться до відповідної групи
    //   - значення – масив з оцінками студента (заповніть масив випадковими значеннями, використовуючи функцію `randomValue(maxValue: Int) -> Int`)

    fun randomValue(maxValue: Int): Int {
        return when((Math.random() * 6 ).toInt()) {
            1 -> kotlin.math.ceil(maxValue.toFloat() * 0.7).toInt()
            2 -> kotlin.math.ceil(maxValue.toFloat() * 0.9).toInt()
            3, 4, 5 -> maxValue
            else -> 0
        }
    }

    val studentPoints = mutableMapOf<String, MutableMap<String, List<Int>>>()

    // Ваш код починається тут

    val groups = studentsGroups.keys;

    for (group in groups) {
        val studentPointsForGroup = mutableMapOf<String, List<Int>>()
        for (student in studentsGroups[ group ]!!) {
            studentPointsForGroup[ student ] = points.map { randomValue(it) }
        }
        studentPoints[ group ] = studentPointsForGroup
    }

    // Ваш код закінчується тут
    println("Завдання 2")
    println(studentPoints)

    // Завдання 3
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – словник, де:
    //   - ключ – студент, який відносяться до відповідної групи
    //   - значення – сума оцінок студента

    val sumPoints = mutableMapOf<String, MutableMap<String, Int>>()

    // Ваш код починається тут

    for (group in groups) {
        val sumPointsForGroup = mutableMapOf<String, Int>()
        val studentPointsForGroup = studentPoints[ group ]!!
        for (student in studentPointsForGroup) {
            val studentName = student.key
            val pointsSumForStudent = studentPointsForGroup[ studentName ]!!.sum()
            sumPointsForGroup[ studentName ] = pointsSumForStudent
        }
        sumPoints[ group ] = sumPointsForGroup
    }

    // Ваш код закінчується тут

    println("Завдання 3")
    println(sumPoints)

    // Завдання 4
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – середня оцінка всіх студентів групи

    val groupAvg = mutableMapOf<String, Float>()

    // Ваш код починається тут

    for (group in groups) {
        val sumPointsForGroup = sumPoints[ group ]
        groupAvg[ group ] = sumPointsForGroup!!.values.sum().toFloat() / (sumPointsForGroup.size)
    }

    // Ваш код закінчується тут

    println("Завдання 4")
    println(groupAvg)

    // Завдання 5
    // Заповніть словник, де:
    // - ключ – назва групи
    // - значення – масив студентів, які мають >= 60 балів

    val passedPerGroup = mutableMapOf<String, List<String>>()

    // Ваш код починається тут

    for (group in groups) {
        val passedList = mutableListOf<String>()
        val sumStudentPoints = sumPoints[ group ]
        for (student in sumStudentPoints!!.keys) {
            val grade = sumStudentPoints[ student ]!!
            if (grade >= 60) passedList += student
        }
        passedPerGroup[ group ] = passedList
    }

    // Ваш код закінчується тут

    println("Завдання 5")
    println(passedPerGroup)
}