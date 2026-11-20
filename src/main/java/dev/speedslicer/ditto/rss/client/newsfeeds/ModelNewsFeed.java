package dev.speedslicer.ditto.rss.client.newsfeeds;

import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import dev.speedslicer.ditto.rss.client.templates.json.config.FeedConfig;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ModelNewsFeed implements Model {
    ListNewsFeed listNewsFeed;
    List<String> splashTexts = List.of("Blud is a cat", "https://github.com/SpeedSlicer/DittoRSS", "Ditto is my favorite pokemon", "<3 Made with love", "i love intellj idea");
    private static int SPLASH_SELECTED;
    public ModelNewsFeed(FeedConfig feedConfig) {
        listNewsFeed = new ListNewsFeed(feedConfig);
        Random random = new Random();
        SPLASH_SELECTED = random.nextInt(splashTexts.size());
    }
    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            return switch (keyPressMessage.key()) {
                case "k", "K", "up", "left" -> UpdateResult.from(this.shiftListUp());

                case "j", "J", "down", "right" -> UpdateResult.from(this.shiftListDown());

                case "enter" -> UpdateResult.from(this.selectHighlightedArticle());

                case "q", "Q" -> UpdateResult.from(this, QuitMessage::new);
                default -> UpdateResult.from(this);
            };
        }
        if (msg instanceof WindowSizeMessage) {
            listNewsFeed.updateWindowSize(((WindowSizeMessage) msg).width(), ((WindowSizeMessage) msg).height());
            return UpdateResult.from(this);
        }

        return UpdateResult.from(this);
    }

    private Model shiftListDown() {
        listNewsFeed.shiftCursor(1);
        return this;
    }

    private Model shiftListUp() {
        listNewsFeed.shiftCursor(-1);
        return this;
    }

    public Model selectHighlightedArticle() {
        try {
            listNewsFeed.openSelectedURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public String view() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(UIConfig.TITLE.render("Shaun's RSS Feed \n \"", splashTexts.get(SPLASH_SELECTED), "\"", " - Shaun"));
        buffer.append("\n");
        buffer.append(listNewsFeed.render());
        return buffer.toString();
    }
}
