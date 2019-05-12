package ru.mrbrikster.baseplugin.menu;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.mrbrikster.baseplugin.plugin.BukkitBasePlugin;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class Menu {

    @Getter private final BukkitBasePlugin bukkitBasePlugin;
    @Getter @Setter private String title;
    private final int lines;

    private final Icon[][] icons = new Icon[9][6];

    public Menu(BukkitBasePlugin bukkitBasePlugin, String title) {
        this(bukkitBasePlugin, title, 6);
    }

    public Menu(BukkitBasePlugin bukkitBasePlugin, String title, int lines) {
        Preconditions.checkNotNull(bukkitBasePlugin);
        Preconditions.checkNotNull(title);
        Preconditions.checkArgument(lines > 0 && lines <= 6);

        this.bukkitBasePlugin = bukkitBasePlugin;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.lines = lines;

        bukkitBasePlugin.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler(priority = EventPriority.MONITOR)
            public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
                Inventory clickedInventory = inventoryClickEvent.getClickedInventory();

                if (clickedInventory != null && clickedInventory.getHolder() instanceof MenuHolder) {
                    MenuHolder menuHolder = (MenuHolder) clickedInventory.getHolder();

                    if (!menuHolder.getMenu().equals(Menu.this))
                        return;

                    int slot = inventoryClickEvent.getSlot() + 1;
                    int y = slot / 9;

                    if (y * 9 != slot)
                        y++;

                    int x = slot % 9;

                    if (x == 0)
                        x = 9;

                    Icon icon = menuHolder.getMenu().get(x, y);

                    if (icon != null) {
                        icon.getHandler().onClick(new ClickIconAction(menuHolder.getMenu(), (Player) inventoryClickEvent.getWhoClicked(), icon));
                    }

                    inventoryClickEvent.setCancelled(true);
                }
            }

            @EventHandler(priority = EventPriority.MONITOR)
            public void onInventoryDrag(InventoryDragEvent inventoryDragEvent) {
                Inventory inventory = inventoryDragEvent.getInventory();

                if (inventory != null && inventory.getHolder() instanceof MenuHolder
                        && ((MenuHolder) inventory.getHolder()).getMenu().equals(Menu.this)) {
                    inventoryDragEvent.setCancelled(true);
                }
            }

            @EventHandler(priority = EventPriority.MONITOR)
            public void onInventoryMoveEvent(InventoryMoveItemEvent inventoryMoveItemEvent) {
                Inventory destinationInventory = inventoryMoveItemEvent.getDestination();
                Inventory initiatorInventory = inventoryMoveItemEvent.getInitiator();

                if (destinationInventory != null && destinationInventory.getHolder() instanceof MenuHolder
                        && ((MenuHolder) destinationInventory.getHolder()).getMenu().equals(Menu.this)) {
                    inventoryMoveItemEvent.setCancelled(true);
                }

                if (initiatorInventory != null && initiatorInventory.getHolder() instanceof MenuHolder
                        && ((MenuHolder) initiatorInventory.getHolder()).getMenu().equals(Menu.this)) {
                    inventoryMoveItemEvent.setCancelled(true);
                }
            }

            @EventHandler(priority = EventPriority.MONITOR)
            public void onInventoryInteract(InventoryInteractEvent inventoryInteractEvent) {
                Inventory inventory = inventoryInteractEvent.getInventory();

                if (inventory != null && inventory.getHolder() instanceof MenuHolder
                        && ((MenuHolder) inventory.getHolder()).getMenu().equals(Menu.this)) {
                    inventoryInteractEvent.setCancelled(true);
                }
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
                Inventory inventory = inventoryCloseEvent.getInventory();

                if (inventory != null && inventory.getHolder() instanceof MenuHolder
                        && ((MenuHolder) inventory.getHolder()).getMenu().equals(Menu.this)) {
                    Player player = (Player) inventoryCloseEvent.getPlayer();

                    if (!onClose(player)) {
                        getBukkitBasePlugin().getScheduler().schedule(() -> Menu.this.open(player), 1, TimeUnit.SECONDS);
                    }
                }
            }

        }, bukkitBasePlugin);
    }

    public boolean onClose(Player player) {
        return true;
    }

    public Icon set(Icon icon, int x, int y) {
        Preconditions.checkArgument(x > 0 && x <= 9);
        Preconditions.checkArgument(y > 0 && y <= lines);

        Icon oldIcon = get(x, y);
        icons[x - 1][y - 1] = icon;
        return oldIcon;
    }

    public Icon get(int x, int y) {
        Preconditions.checkArgument(x > 0 && x <= 9);
        Preconditions.checkArgument(y > 0 && y <= lines);

        return icons[x - 1][y - 1];
    }

    public Inventory open(Player player) {
        Preconditions.checkNotNull(player);

        MenuHolder menuHolder = new MenuHolder(null, this);
        Inventory inventory = bukkitBasePlugin.getServer().createInventory(menuHolder, lines * 9, title);
        menuHolder.setInventory(inventory);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < lines; j++) {
                Icon icon = icons[i][j];

                if (icon == null)
                    continue;

                ItemStack itemStack = new ItemStack(icon.getType());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', icon.getName()));
                itemMeta.setLore(icon.getLore().stream().map((str) -> ChatColor.translateAlternateColorCodes('&', str)).collect(Collectors.toList()));
                itemMeta.addItemFlags(ItemFlag.values());

                itemStack.setItemMeta(itemMeta);

                if (icon.isEnchant()) {
                    itemStack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                }

                inventory.setItem(i + j * 9, itemStack);
            }
        }

        player.openInventory(inventory);

        return inventory;
    }

}
