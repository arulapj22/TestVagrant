package org.test;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecentlyPlayedStore {

	private final int capacity;
	private final Map<String, LinkedHashMap<String, String>> userPlaylists;

	public RecentlyPlayedStore(int capacity) {
		this.capacity = capacity;
		this.userPlaylists = new LinkedHashMap<>(capacity, 0.75f, true);
	}

	public void playSong(String user, String song) {
		userPlaylists.computeIfAbsent(user, k -> new LinkedHashMap<String, String>() {
			@Override
			protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
				return size() > capacity;
			}
		}).put(song, song);
	}

	public String getRecentlyPlayedSongs(String user) {
		return String.join(",", userPlaylists.getOrDefault(user, new LinkedHashMap<>()).keySet());
	}

	public static void main(String[] args) {
		RecentlyPlayedStore store = new RecentlyPlayedStore(3);

		store.playSong("user1", "S1");
		store.playSong("user1", "S2");
		store.playSong("user1", "S3");
		System.out.println(store.getRecentlyPlayedSongs("user1")); // S1,S2,S3

		store.playSong("user1", "S4");
		System.out.println(store.getRecentlyPlayedSongs("user1")); // S2,S3,S4

		store.playSong("user1", "S2");
		System.out.println(store.getRecentlyPlayedSongs("user1")); // S3,S4,S2

		store.playSong("user1", "S1");
		System.out.println(store.getRecentlyPlayedSongs("user1")); // S4,S2,S1
	}
}
