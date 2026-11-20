package dev.speedslicer.ditto.rss.client.newsfeeds;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.apptasticsoftware.rssreader.filter.InvalidXmlCharacterFilter;
import com.williamcallahan.tui4j.compat.bubbles.list.DefaultItem;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import dev.speedslicer.ditto.rss.client.templates.json.config.FeedConfig;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

record ArticleItem(String title, String desc) implements DefaultItem {
    @Override
    public String filterValue() {
        return title;
    }

    @Override
    public String description() {
        return desc;
    }

    @NotNull
    @Override
    public String toString() {
        return title + ": " + desc;
    }
}

public class ListNewsFeed {
    private int cursor;
    private String choice;
    FeedConfig feedConfig;
    List<Item> items;
    private static int SELECTED_ARTICLE_NUMBER = 0;
    List<ArticleItem> articleitemsList = new ArrayList<>();
    public static int OTHER_ARTICLE_AMOUNT = 8, TRUNCATE_CHARACTER_AMOUNT = 16;
    private HashMap<ArticleItem, Item> itemMap = new HashMap<>();

    public ListNewsFeed(FeedConfig feedConfig) {
        this.feedConfig = feedConfig;
        parseRSSFiles();
    }

    public static int getSelectedArticleNumber() {
        return SELECTED_ARTICLE_NUMBER;
    }

    public static void setSelectedArticleNumber(int selectedArticleNumber) {
        SELECTED_ARTICLE_NUMBER = selectedArticleNumber;
    }

    public void parseRSSFiles() {
        RssReader reader = new RssReader();
        reader.addFeedFilter(new InvalidXmlCharacterFilter());
        items = reader.read(feedConfig.feeds.values()).toList();
    }

    public String render() {
        articleitemsList = items.stream().map(
                x -> {
                    ArticleItem articleItem = null;
                    if (x.getTitle().isPresent() && x.getDescription().isPresent()) {
                        articleItem = new ArticleItem(x.getTitle().get(), x.getDescription().get());
                    }
                    else if (x.getTitle().isPresent()) {
                        articleItem = new ArticleItem(x.getTitle().get(), "No desc.");
                    }
                    itemMap.put(articleItem, x);
                    return articleItem;
                }
        ).toList();

        List<String> stringBuilder = createArticleList(UIConfig.ARTICLE_ITEM_SELECTED, UIConfig.ARTICLE_ITEM_UNSELECTED);
        return UIConfig.LIST_WINDOW_STYLE.render(stringBuilder.toArray(new String[0]));
    }

    @NotNull
    private List<String> createArticleList(Style selectedStyle, Style unselectedStyle) {
        ArrayList<String> lines = new ArrayList<>();
        int amountBefore = Math.min(SELECTED_ARTICLE_NUMBER, OTHER_ARTICLE_AMOUNT / 2);
        for (int i = 1; i <= amountBefore; i++) {
            if (0 <= SELECTED_ARTICLE_NUMBER - i) {
                lines.add(unselectedStyle.render(articleitemsList.get(SELECTED_ARTICLE_NUMBER - i).toString()));
                lines.add("\n");
            }
        }
        lines.add(selectedStyle.render(" | " + articleitemsList.get(SELECTED_ARTICLE_NUMBER) + " | "));
        lines.add("\n");

        int amountAfter = OTHER_ARTICLE_AMOUNT - amountBefore;
        for (int j = 1; j <= amountAfter; j++) {
            if (articleitemsList.size() > SELECTED_ARTICLE_NUMBER + j) {
                lines.add(unselectedStyle.render(articleitemsList.get(SELECTED_ARTICLE_NUMBER + j).toString()));
                lines.add("\n");
            }
        }
        return lines;
    }

    public int clampArticleNumber(int number) {
        return Math.clamp(number , 0, items.size() - 1);
    }
    public void shiftCursor(int amount) {
        SELECTED_ARTICLE_NUMBER = clampArticleNumber(SELECTED_ARTICLE_NUMBER + amount);
    }

    public void openSelectedURL() throws URISyntaxException, IOException {
        Optional<String> url = itemMap.get(articleitemsList.get(SELECTED_ARTICLE_NUMBER)).getLink();
        if (url.isPresent()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url.get()));
            }
            else {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url.get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateWindowSize(int width, int height) {
        OTHER_ARTICLE_AMOUNT = height - 3;
    }
}
