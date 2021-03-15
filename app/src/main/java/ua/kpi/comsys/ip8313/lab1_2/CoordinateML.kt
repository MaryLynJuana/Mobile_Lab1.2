package ua.kpi.comsys.ip8313.lab1_2

import java.security.InvalidParameterException
import kotlin.math.abs
import kotlin.math.truncate

enum class Direction(val minDegrees: Int, val maxDegrees: Int,
                     val minMinutes: UInt = 0u, val maxMinutes: UInt = 59u,
                     val minSeconds: UInt = 0u, val maxSeconds: UInt = 59u) {
    LATITUDE(-90, 90),
    LONGITUDE(-180, 180)
}

class CoordinateML(private val direction: Direction) {
    private var degrees: Int = 0
    private var minutes : UInt = 0u
    private var seconds : UInt = 0u
    constructor(
            direction: Direction, degrees: Int = 0, minutes: UInt = 0u, seconds: UInt = 0u
    ) : this(direction) {
        if (degrees < direction.minDegrees || degrees > direction.maxDegrees) {
            throw InvalidParameterException(
                "Degrees value should be between ${direction.minDegrees} and ${direction.maxDegrees}"
            )
        }
        if (minutes < direction.minMinutes || minutes > direction.maxMinutes) {
            throw InvalidParameterException(
                "Minutes value should be between ${direction.minMinutes} and ${direction.maxMinutes}"
            )
        }
        if (seconds < direction.minSeconds || seconds > direction.maxSeconds) {
            throw InvalidParameterException(
                "Seconds value should be between ${direction.minSeconds} and ${direction.maxSeconds}"
            )
        }
        if (degrees == direction.maxDegrees && (minutes > 0u || seconds > 0u)) {
            throw InvalidParameterException(
                "Coordinate value shouldn't be higher than ${direction.maxDegrees}°0′0″"
            )
        }
        if (degrees == direction.minDegrees && (minutes > 0u || seconds > 0u)) {
            throw InvalidParameterException(
                    "Coordinate value shouldn't be lower than ${direction.minDegrees}°0′0″"
            )
        }
        this.degrees = degrees
        this.minutes = minutes
        this.seconds = seconds
    }

    private fun getDirection(): String {
        return when (this.direction) {
            Direction.LATITUDE -> {
                if (this.degrees >= 0) "N"
                else "S"
            }
            Direction.LONGITUDE -> {
                if (this.degrees >= 0) "E"
                else "W"
            }
        }
    }

    private fun getDecimal(): Float {
        val minsAndSecs = (this.minutes.toFloat() + this.seconds.toFloat() / 60) / 60
        return if (this.degrees > 0 ) this.degrees.toFloat() + minsAndSecs
                else this.degrees.toFloat() - minsAndSecs
    }

    fun getValueForPrint(): String {
        val dir = this.getDirection()
        return "${abs(degrees)}°${minutes}′${seconds}″ $dir"
    }

    fun getDecimalForPrint(): String {
        return "${abs(this.getDecimal())}° ${this.getDirection()}"
    }

    companion object {
        @JvmStatic
        private fun revertFromDecimal(decimalVal: Float): Triple<Int, UInt, UInt> {
            val degrees = truncate(decimalVal).toInt()
            val minsAndSecs = abs((decimalVal - degrees) * 60)
            val minutes = truncate(minsAndSecs).toUInt()
            val seconds = truncate((minsAndSecs - minutes.toDouble()) * 60).toUInt()
            return Triple(degrees, minutes, seconds)
        }
        @JvmStatic
        fun getHalfwayCoordBetween(start: CoordinateML, end: CoordinateML): CoordinateML? {
            if (start.direction != end.direction) return null
            val halfway = (start.getDecimal() + end.getDecimal()) / 2
            val (halfwayDegrees, halfwayMinutes, halfwaySeconds) = CoordinateML.revertFromDecimal(halfway)
            return CoordinateML(start.direction, halfwayDegrees, halfwayMinutes, halfwaySeconds)
        }
    }

    fun getHalfwayCoordTo(coord: CoordinateML): CoordinateML? {
        if (this.direction != coord.direction) return null
        val start = this.getDecimal()
        val end = coord.getDecimal()
        val halfway = (start + end) / 2
        val (halfwayDegrees, halfwayMinutes, halfwaySeconds) = CoordinateML.revertFromDecimal(halfway)
        return CoordinateML(this.direction, halfwayDegrees, halfwayMinutes, halfwaySeconds)
    }
}

fun main() {
    println("Створення екземпляру координати з нульовими значеннями за замовчюванням")
    val coord1 = CoordinateML(Direction.LATITUDE)
    println(coord1.getValueForPrint())
    println("Створення екземпляру координати з заданим набором значень (градуси, мінути, секунди)")
    val coord2 = CoordinateML(Direction.LATITUDE, 89, 59u,5u)
    println(coord2.getValueForPrint())
    val coord3 = CoordinateML(Direction.LONGITUDE, -179, 14u,10u)
    println("Виведення результату методу, що повертає рядок з градусами, мінутами, секундами")
    println(coord3.getValueForPrint())
    println("Виведення результату методу, що повертає рядок з десятковим значенням координати")
    println(coord3.getDecimalForPrint())
    val coord4 = CoordinateML(Direction.LATITUDE, 20, 0u, 0u)
    val coord5 = CoordinateML(Direction.LATITUDE, -40, 0u, 0u)
    val coord6 = coord4.getHalfwayCoordTo(coord5)
    println("Виведення результату методу координати 5, що повертає середню координату між координатами 5 і 6")
    println("${coord6?.getValueForPrint()} is between ${coord4.getValueForPrint()} and ${coord5.getValueForPrint()}")
    println("Виведення результату методу класу, що повертає середню координату між координатами-параметрами")
    val coord7 = CoordinateML.getHalfwayCoordBetween(coord2, coord4)
    println("${coord7?.getValueForPrint()} is between ${coord2.getValueForPrint()} and ${coord4.getValueForPrint()}")
}