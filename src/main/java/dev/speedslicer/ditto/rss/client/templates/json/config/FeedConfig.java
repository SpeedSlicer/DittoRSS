package dev.speedslicer.ditto.rss.client.templates.json.config;

import java.util.Map;

public class FeedConfig {
    public final Map<String, String> feeds;

    public FeedConfig(Map<String, String> feeds) {
        this.feeds = feeds;
    }
}
