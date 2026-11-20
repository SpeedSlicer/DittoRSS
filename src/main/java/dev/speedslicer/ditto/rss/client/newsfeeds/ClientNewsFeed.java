package dev.speedslicer.ditto.rss.client.newsfeeds;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.apptasticsoftware.rssreader.filter.InvalidXmlCharacterFilter;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import dev.speedslicer.ditto.rss.client.templates.json.config.FeedConfig;

import java.util.List;

public class ClientNewsFeed {
    public ClientNewsFeed(FeedConfig feedConfig) {
        ModelNewsFeed modelNewsFeed = new ModelNewsFeed(feedConfig);
        new Program(modelNewsFeed).withAltScreen().withKittyKeyboard().run();
    }
}
