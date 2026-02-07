import org.junit.jupiter.api.Test
import kotlin.test.*

class algoTests {
    @Test
    fun handle_case1() {
        val testData = "2 1\n" +
                "1 5 15\n" +
                "2 5 15\n" +
                "1 2\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        expected_result.getOrPut(2){ HashSet() }.add(15)
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun unload_while_arrive() {
        val testData = "2 1\n" +
                "1 5 15\n" +
                "2 15 5\n" +
                "1 2\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        expected_result.getOrPut(2){ HashSet() }.add(15)
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun one_station() {
        val testData = "1 0\n" +
                "1 1 2\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun one_station_cycle() {
        val testData = "1 1\n" +
                "1 1 2\n" +
                "1 1\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        expected_result.getOrPut(1){ HashSet() }.add(2)
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun one_station_cycle_equal_load_unload() {
        val testData = "1 1\n" +
                "1 5 5\n" +
                "1 1\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        expected_result.getOrPut(1){ HashSet() }.add(5)
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun isolated_start_point() {
        val testData = "3 1\n" +
                "1 1 2\n" +
                "2 2 3\n" +
                "3 3 4\n" +
                "2 3\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun large_cargo_types() {
        val testData = "3 2\n" +
                "1 23453451 1234322\n" +
                "2 578 12343\n" +
                "3 123 987\n" +
                "1 2\n" +
                "2 3\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        expected_result.getOrPut(2){ HashSet() }.add(1234322)
        expected_result.getOrPut(3){ HashSet() }.add(12343)
        expected_result.getOrPut(3){ HashSet() }.add(1234322)
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun large_station_id() {
        val testData = "2 1\n" +
                "13123 5 15\n" +
                "6994596 5 15\n" +
                "13123 6994596\n" +
                "13123"
        val expected_result = HashMap<Int, HashSet<Int>>()
        expected_result.getOrPut(6994596){ HashSet() }.add(15)
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun no_child_start_station() {
        val testData = "2 1\n" +
                "1 5 15\n" +
                "2 5 15\n" +
                "1 2\n" +
                "2"
        val expected_result = HashMap<Int, HashSet<Int>>()
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
    @Test
    fun unload_before_arrival() {
        val testData = "3 2\n" +
                "1 10 20\n" +
                "2 20 30\n" +
                "3 30 40\n" +
                "1 2\n" +
                "2 3\n" +
                "1"
        val expected_result = HashMap<Int, HashSet<Int>>()
        expected_result.getOrPut(2){ HashSet() }.add(20)
        expected_result.getOrPut(3){ HashSet() }.add(30)
        val real_result = simulate_handle_input(testData)
        assertEquals(expected_result, real_result)
    }
}