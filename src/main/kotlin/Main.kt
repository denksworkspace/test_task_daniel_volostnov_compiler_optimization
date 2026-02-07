fun find_reachable_stations(edges: Array<HashSet<Int>>,
                            visited: BooleanArray,
                            current_vertex: Int) {
    visited[current_vertex] = true
    for (child in edges[current_vertex]) {
        if (visited[child]) {
            continue
        }
        find_reachable_stations(edges, visited, child)
    }
}

fun update_cargo_arrival_stations(edges: Array<HashSet<Int>>,
                                  arrival_stations_to_update: Array<HashSet<Int>>,
                                  blocked_stations: HashSet<Int>,
                                  current_vertex: Int,
                                  current_cargo_type: Int) {
    for (child in edges[current_vertex]) {
        val need_to_stop: Boolean = arrival_stations_to_update[child].contains(current_cargo_type) || blocked_stations.contains(child);
        if (!arrival_stations_to_update[child].contains(current_cargo_type)) {
            arrival_stations_to_update[child].add(current_cargo_type)
        }
        if (need_to_stop) {
            continue
        }
        update_cargo_arrival_stations(edges, arrival_stations_to_update,
            blocked_stations, child, current_cargo_type)
    }
}

fun solve(input_data: String): HashMap<Int, HashSet<Int>> {
    val input = input_data.trim().split(Regex("\\s+")).iterator()
    val S = input.next().toInt()
    val T = input.next().toInt()
    val zip_station = HashMap<Int, Int>()
    val unzip_station = HashMap<Int, Int>()
    val unload_stations = HashMap<Int, HashSet<Int>>()
    val load_stations = HashMap<Int, HashSet<Int>>()
    val terminal_stations = HashMap<Int, HashSet<Int>>()
    val station_cargo_types = Array(S){ Pair(-1, -1) }
    val station_connection = Array(S){ HashSet<Int>() }
    val reachable_station = BooleanArray(S)
    val possible_cargo_types_arrival = Array(S) { HashSet<Int>() }
    repeat(S) {
        val station_id = input.next().toInt()
        val s = zip_station.getOrPut(station_id){ zip_station.size }
        unzip_station.getOrPut(s){ station_id }
        val c_unload = input.next().toInt()
        val c_load = input.next().toInt()
        unload_stations.getOrPut(c_unload) { HashSet() }.add(s)
        terminal_stations.getOrPut(c_unload) { HashSet() }.add(s)
        load_stations.getOrPut(c_load) { HashSet() }.add(s)
        terminal_stations.getOrPut(c_load) { HashSet() }.add(s)
        station_cargo_types[s] = Pair(c_unload, c_load)
    }
    repeat(T) {
        val s_from = zip_station[input.next().toInt()]!!
        val s_to = zip_station[input.next().toInt()]!!
        station_connection[s_from].add(s_to)
    }
    val s0 = zip_station[input.next().toInt()]!!
    find_reachable_stations(station_connection, reachable_station, s0)
    for (station_id in 0..<S) {
        if (reachable_station[station_id]) {
            val cargo_to_update = station_cargo_types[station_id].second
            if (possible_cargo_types_arrival[station_id].contains(cargo_to_update)) {
                continue
            }
            update_cargo_arrival_stations(station_connection, possible_cargo_types_arrival, terminal_stations[cargo_to_update] ?: HashSet(), station_id, cargo_to_update)
        }
    }
    val unziped_possible_cargo_types_arrival = HashMap<Int, HashSet<Int>>()
    for (station_id in 0..<S) {
        for (cargo_type in possible_cargo_types_arrival[station_id]) {
            val unzip_id = unzip_station[station_id] ?: continue
            unziped_possible_cargo_types_arrival.getOrPut(unzip_id){ HashSet() }.add(cargo_type)
        }
    }
    return unziped_possible_cargo_types_arrival
}

fun simulate_handle_input(test_data: String): HashMap<Int, HashSet<Int>> {
    return solve(test_data)
}


fun main() {
    var inputData = ""
    val (input_S, input_T) = readln().trim().split(" ").map{ it.toInt() }
    inputData += "$input_S $input_T\n"
    repeat(input_S) {
        val (input_s, input_c_unload, input_c_load) = readln().trim().split(" ").map{ it.toInt() }
        inputData += "$input_s $input_c_unload $input_c_load\n"
    }
    repeat(input_T) {
        val (input_s_from, input_s_to) = readln().trim().split(" ").map { it.toInt() }
        inputData += "$input_s_from $input_s_to\n"
    }
    val input_s0 = readln()
    inputData += input_s0
    val result = solve(inputData)
    for ((station_id, cargoes) in result) {
        print("station_$station_id ")
        for (cargo in cargoes) {
            print("cargo_$cargo ")
        }
        println()
    }
}
