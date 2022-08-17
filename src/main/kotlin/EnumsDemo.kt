package com.demo.enumsdemo

enum class CoffeeCupSize(val quantity: Int, val price: Float) {
    MEGA(100, 1.5f) {
        override fun getSize(): String {
            return "$this.quantity ml"
        }
        override fun getInfo(): String {
            return "The $name drink has $quantity ml of coffee"
        }
        override fun calcCost(): (Order) -> Float {
            return { order -> order.numCups * price }
        }
    },
    GRANDE(50, 1.0f) {
        override fun getSize(): String {
            return "$this.quantity ml"
        }
        override fun getInfo(): String {
            return "The $name drink has $quantity ml of coffee"
        }
        override fun calcCost(): (Order) -> Float {
            return { order -> order.numCups * price }
        }
    },
    MEDIUM(30, 0.5f) {
        override fun getSize(): String {
            return "$this.quantity ml"
        }
        override fun getInfo(): String {
            return "The $name drink has $quantity ml of coffee"
        }
        override fun calcCost(): (Order) -> Float {
            return { order -> order.numCups * price }
        }
    },
    SMALL(10, 0.25f) {
        override fun getSize(): String {
            return "$this.quantity ml"
        }
        override fun getInfo(): String {
            return "The $name drink has $quantity ml of coffee"
        }
        override fun calcCost(): (Order) -> Float {
            return { order -> order.numCups * price }
        }
    };


    abstract fun getSize(): String
    abstract fun getInfo(): String
    abstract fun calcCost(): (Order) -> Float

}

fun priceCalculator(coffeeCupSize: CoffeeCupSize): (Order)->Float {
    when(coffeeCupSize) {
        CoffeeCupSize.MEGA -> return { order -> order.numCups * 1.5f }
        CoffeeCupSize.GRANDE -> return { order -> order.numCups * 1.0f }
        CoffeeCupSize.MEDIUM -> return { order -> order.numCups * 0.5f }
        CoffeeCupSize.SMALL -> return { order -> order.numCups * 0.25f }
    }
}

class Order(var numCups: Int)
class OrderOfCoffee(var numCups: Int, val coffeeCupSize: CoffeeCupSize) {
    val price = priceCalculator(coffeeCupSize)(Order(numCups))
}

fun main(array: Array<String>) {

    val order = Pair(CoffeeCupSize.SMALL, Order(10))

    println("priceCalculator(order.first)(order.second) = ${priceCalculator(order.first)(order.second)}")
    println("priceCalculator(order.first).invoke(order.second) = ${priceCalculator(order.first).invoke(order.second)}") // does same as line above


    val orderArr = listOf(
        Pair(CoffeeCupSize.MEGA, Order(1)),
        Pair(CoffeeCupSize.GRANDE, Order(2)),
        Pair(CoffeeCupSize.MEDIUM, Order(2)),
        Pair(CoffeeCupSize.SMALL, Order(3))
    )
    val orderArrPrice = orderArr.fold(0f) { acc, pair ->
        acc + priceCalculator(pair.first)(pair.second)
    }
    println("orderArrPrice = $orderArrPrice")


    val orderOfCoffee = OrderOfCoffee(10, CoffeeCupSize.MEGA)
    println("orderOfCoffee.price =${orderOfCoffee.price}")


    val orderOfCoffee2 = OrderOfCoffee(10, CoffeeCupSize.MEGA)
    println("orderOfCoffee2.coffeeCupSize.calcCost() =${orderOfCoffee2.coffeeCupSize.calcCost()(Order(10))}")

    orderArr.forEach {cup ->
        println("priceCalculator(it.first)(it.second): ${cup.first.getInfo()} = ${priceCalculator(cup.first)(cup.second)}")
    }

    var sum = 0f
    orderArr.forEach {cup ->
        priceCalculator(cup.first)(cup.second).also { sum += it }
    }
    println("orderArr.sum = $sum")

}