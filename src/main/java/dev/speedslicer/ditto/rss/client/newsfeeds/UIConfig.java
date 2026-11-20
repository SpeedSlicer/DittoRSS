package dev.speedslicer.ditto.rss.client.newsfeeds;

import com.williamcallahan.tui4j.compat.lipgloss.Position;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import com.williamcallahan.tui4j.compat.lipgloss.color.Color;

public class UIConfig {

    public static final Style LIST_WINDOW_STYLE = Style.newStyle().marginBottom(0).marginLeft(5);
    public static final Style ARTICLE_ITEM_UNSELECTED = Style
            .newStyle()
            .foreground(Color.color("#f4ffff"));
    public static final Style ARTICLE_ITEM_SELECTED = Style
            .newStyle()
            .foreground(Color.color("#ffccff")).bold(true);
    public static final Style TITLE = Style.newStyle().foreground(Color.color("#ff6be6"))
            .bold(true).alignHorizontal(Position.Center).alignVertical(Position.Top);

}
