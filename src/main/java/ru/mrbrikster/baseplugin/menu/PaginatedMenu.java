package ru.mrbrikster.baseplugin.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import ru.mrbrikster.baseplugin.plugin.BukkitBasePlugin;

import java.util.List;

public abstract class PaginatedMenu extends Menu {

    private final List<Icon> iconList;
    private final int maxPages;
    private final int lines;
    @Getter @Setter int page = 1;

    private Icon previousPageIcon =
            Icon.builder()
                    .name("&aПредыдущая страница")
                    .type(Material.ARROW)
                    .handler((clickIconAction) -> {
                        page -= 1;
                        generatePage();
                        open(clickIconAction.getPlayer());
                    })
                    .build();

    private Icon nextPageIcon =
            Icon.builder()
                    .name("&aСледующая страница")
                    .type(Material.ARROW)
                    .handler((clickIconAction) -> {
                        page += 1;
                        generatePage();
                        open(clickIconAction.getPlayer());
                    })
                    .build();

    public PaginatedMenu(BukkitBasePlugin bukkitBasePlugin, String title, int lines, List<Icon> iconList) {
        super(bukkitBasePlugin, title, lines + 1);

        this.iconList = iconList;
        this.maxPages = iconList.size() / (lines * 9) + (iconList.size() % (lines * 9) == 0 ? 0 : 1);
        this.lines = lines;

        generatePage();
    }

    public PaginatedMenu(BukkitBasePlugin bukkitBasePlugin, String title, int lines, List<Icon> iconList, Icon previousPageIcon, Icon nextPageIcon) {
        this(bukkitBasePlugin, title, lines, iconList);

        this.previousPageIcon = previousPageIcon;
        this.previousPageIcon.setHandler((clickIconAction) -> {
            page -= 1;
            generatePage();
            open(clickIconAction.getPlayer());
        });

        this.nextPageIcon = nextPageIcon;
        this.nextPageIcon.setHandler((clickIconAction) -> {
            page += 1;
            generatePage();
            open(clickIconAction.getPlayer());
        });
    }

    private void generatePage() {
        if (page == 1) {
            set(null, 1, lines + 1);
        } else {
            set(previousPageIcon, 1, lines + 1);
        }

        if (page == maxPages) {
            set(null, 9, lines + 1);
        } else {
            set(nextPageIcon, 9, lines + 1);
        }

        for (int k = 1; k <= lines; k++) {
            for (int i = 1; i <= 9; i++) {
                set(null, i, k);
            }
        }

        for (int k = 1; k <= lines; k++) {
            for (int i = 1; i <= 9; i++) {
                int slot = ((k - 1) * 9 + i) + (page - 1) * lines * 9;

                if ((slot - 1) >= iconList.size())
                    break;

                set(iconList.get(slot - 1), i, k);
            }
        }
    }

}
