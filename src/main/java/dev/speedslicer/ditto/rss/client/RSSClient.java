package dev.speedslicer.ditto.rss.client;

import com.williamcallahan.tui4j.compat.bubbletea.Program;
import dev.speedslicer.ditto.rss.client.templates.json.config.FeedConfig;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class RSSClient {
    FeedConfig feedConfig;
    Path configFolder, feedFile;
    Program program;
    RSSClientMainMenu clientMainMenu;
    public RSSClient() throws IOException {
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
            Files.createFile(feedFile);
            URL resource = Main.class.getResource("default_feeds.json");

        }
        clientMainMenu = new RSSClientMainMenu();
        program = new Program(clientMainMenu);
        program.run();

        if (clientMainMenu.getChoice() == null) {
            return;
        }
        System.out.printf("\n---\nYou chose: %s!\n", clientMainMenu.getChoice());
    }
}
