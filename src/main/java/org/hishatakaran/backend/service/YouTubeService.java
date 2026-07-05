package org.hishatakaran.backend.service;

import java.net.URI;
import java.util.List;

public class YouTubeService {

    public static String extractVideoId(String url) {
        URI uri = URI.create(url);
        String host = uri.getHost();

        if (host == null) {
            throw new IllegalArgumentException("Invalid URL");
        }

        if (host.contains("youtu.be")) {
            return uri.getPath().substring(1);
        }

        if (host.contains("youtube.com")) {

            String query = uri.getQuery();
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2 && pair[0].equals("v")) {
                        return pair[1];
                    }
                }
            }

            String[] parts = uri.getPath().split("/");
            List<String> list = List.of(parts);

            if (list.contains("embed")) {
                return list.get(list.indexOf("embed") + 1);
            }

            if (list.contains("shorts")) {
                return list.get(list.indexOf("shorts") + 1);
            }
        }

        throw new IllegalArgumentException("Cannot extract YouTube video ID");
    }
}