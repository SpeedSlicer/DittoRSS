package dev.speedslicer.ditto.rss.client;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import dev.speedslicer.ditto.rss.client.newsfeeds.ClientNewsFeed;
import dev.speedslicer.ditto.rss.client.templates.json.config.FeedConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Client {
    FeedConfig feedConfig;
    Path configFolder, feedFile;
    Program program;
    public static final Logger LOGGER = LogManager.getLogManager().getLogger("RSSClient");
    
    public Client() {
        try {
            loadConfig();
        }
        catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Config Failed to Load");
            exception.printStackTrace();
        }

    }

    public void loadConfig() throws IOException{
        // Read from main feed config thing
        // Source - https://stackoverflow.com/a/2837329
        // Posted by Bozho, modified by community. See post 'Timeline' for change history
        // Retrieved 2026-11-17, License - CC BY-SA 3.0
        configFolder = Path.of(new File(".").getAbsolutePath()).resolve("config");
        
        feedFile = configFolder.resolve("feeds.json");
        if(Files.notExists(configFolder)) {
            Files.createDirectory(configFolder);
        }
        if (Files.notExists(feedFile)) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream is = classLoader.getResourceAsStream("default_feeds.json");
            Files.createFile(feedFile);
            is.transferTo(Files.newOutputStream(feedFile));
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<FeedConfig> feedJsonAdapter = moshi.adapter(FeedConfig.class);
        feedConfig = feedJsonAdapter.fromJson(Files.newBufferedReader(feedFile).readAllAsString());
        new ClientNewsFeed(feedConfig);

    }
}
