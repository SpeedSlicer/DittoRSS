package dev.speedslicer.ditto.rss.client;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.apptasticsoftware.rssreader.filter.InvalidXmlCharacterFilter;

import dev.speedslicer.ditto.rss.client.templates.json.config.FeedConfig;

public class RSSClientNewsFeeds {
    FeedConfig feedConfig;
    List<Item> 
    public RSSClientNewsFeeds(FeedConfig feedConfig) {
        this.feedConfig = feedConfig;
        parseRSSFiles();
    }
    public void parseRSSFiles() {
            new RssReader()
               .addFeedFilter(new InvalidXmlCharacterFilter())
               .read(feedConfig.feeds)
               .sorted()
               .map(Item::getTitle).collect(
    }
}
