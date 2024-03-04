package me.wiefferink.areashop.commands.cloud;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.wiefferink.areashop.MessageBridge;
import me.wiefferink.areashop.commands.util.GeneralRegionParser;
import me.wiefferink.areashop.managers.IFileManager;
import me.wiefferink.areashop.regions.GeneralRegion;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.bean.CommandProperties;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.parser.standard.EnumParser;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@Singleton
public class SchematicEventCloudCommand extends CloudCommandBean {

    private static final CloudKey<GeneralRegion> KEY_REGION = CloudKey.of("region", GeneralRegion.class);
    private static final CloudKey<GeneralRegion.RegionEvent> KEY_EVENT_TYPE = CloudKey.of("eventType",
            GeneralRegion.RegionEvent.class);
    private final MessageBridge messageBridge;
    private final IFileManager fileManager;

    @Inject
    public SchematicEventCloudCommand(@Nonnull MessageBridge messageBridge, @Nonnull IFileManager fileManager) {
        this.messageBridge = messageBridge;
        this.fileManager = fileManager;
    }

    @Override
    public String stringDescription() {
        return null;
    }

    @NotNull
    @Override
    protected Command.Builder<? extends CommandSender> configureCommand(@NotNull Command.Builder<CommandSender> builder) {
        return builder.literal("schemevent")
                .permission("areashop.schematicevents")
                .required(KEY_REGION, GeneralRegionParser.generalRegionParser(this.fileManager))
                .required(KEY_EVENT_TYPE, EnumParser.enumParser(GeneralRegion.RegionEvent.class))
                .handler(this::handleCommand);
    }

    @Override
    protected @NonNull CommandProperties properties() {
        return CommandProperties.of("schemevent");
    }

    public String getHelp(CommandSender target) {
        if (target.hasPermission("areashop.schematicevents")) {
            return "help-schemevent";
        }
        return null;
    }

    private void handleCommand(@Nonnull CommandContext<CommandSender> context) {
        CommandSender sender = context.sender();
        if (!sender.hasPermission("areashop.schematicevents")) {
            messageBridge.message(sender, "schemevent-noPermission");
            return;
        }

        GeneralRegion region = context.get(KEY_REGION);
        GeneralRegion.RegionEvent event = context.get(KEY_EVENT_TYPE);
        if (region.getRegion() == null) {
            messageBridge.message(sender, "general-noRegion", region);
            return;
        }
        region.handleSchematicEvent(event);
        region.update();
        messageBridge.message(sender, "schemevent-success", event.getValue(), region);
    }

}