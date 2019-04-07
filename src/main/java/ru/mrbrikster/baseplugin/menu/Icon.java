package ru.mrbrikster.baseplugin.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.bukkit.Material;

import java.util.List;

@Builder
public class Icon {

    @Getter @NonNull private final Material type;
    @Getter @NonNull private final String name;
    @Getter @Builder.Default private final boolean enchant = false;
    @Getter @Singular("lore") private final List<String> lore;
    @Getter @Builder.Default private final ClickIconAction.ClickHandler handler = (clickIconAction) -> {};

}
