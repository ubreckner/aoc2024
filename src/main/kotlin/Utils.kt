import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest
import java.time.LocalDate
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()
/**
 * Reads lines from the given input txt file.
 */
fun loadInput(day: Int): List<String> {
    val file = File("src/main/resources/days", "Day$day.txt")
    val date = LocalDate.now()
    var res = emptyList<String>()

    if (date.dayOfMonth >= day && !file.exists()) {
        downloadInput(day)
    }
    if (file.exists()) {
        res = file.readLines()
    }
    return res
}

fun loadTestInput(day: Int, part: Int): List<String> {
    val file = File("src/main/resources/days", "Day$day-$part-test.txt")
    if (!file.exists()) {
        return emptyList()
    }
    return file.readLines()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

private fun downloadInput(day: Int) {
    val properties = Properties()
    val propFile = File("src/main/resources", "aoc.properties")
    FileInputStream(propFile).use { properties.load(it) }

    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://adventofcode.com/2024/day/$day/input"))
        .header(
            "cookie",
            properties.getProperty("my.personal.cookie")
        )
        .header("User-Agent", "github.com/ubreckner/aoc2023 Contact: X @V1perGames")
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    File("src/main/resources/days", "Day$day.txt").bufferedWriter()
        .use { writer -> writer.write(response.body()) }
}
