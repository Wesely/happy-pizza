package tw.wesley.uiassignment.extensions

fun List<Int>.median(): Int? {
    val sortedList = sorted()
    if (isEmpty()) {
        null
    }
    val middle = size / 2
    return if (size % 2 == 0) {
        (sortedList[middle - 1] + sortedList[middle]) / 2
    } else {
        sortedList[middle]
    }
}
