package me.wiefferink.areashop.commands.util;

import me.wiefferink.areashop.tools.DurationInput;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DurationInputParser<C> implements ArgumentParser<C, DurationInput> {

    public static <C> ParserDescriptor<C, DurationInput> durationInputParser() {
        return ParserDescriptor.of(new DurationInputParser<>(), DurationInput.class);
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull DurationInput> parse(@NonNull CommandContext<@NonNull C> commandContext,
                                                                      @NonNull CommandInput commandInput) {
        String input = commandInput.peekString();
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isAlphabetic(input.charAt(i))) {
                index = i;
                break;
            }
        }
        if (index == commandInput.length() - 1) {
            return ArgumentParseResult.failure(new AreaShopCommandException("setduration-wrongAmount", input));
        }
        String duration = input.substring(0, index);
        String durationUnit = input.substring(index, input.length() - 1);
        int durationInt;
        try {
            durationInt = Integer.parseInt(duration);
        } catch (NumberFormatException ex) {
            return ArgumentParseResult.failure(new AreaShopCommandException("setduration-wrongAmount", duration));
        }
        Optional<TimeUnit> timeUnit = DurationInput.getTimeUnit(durationUnit);
        if (timeUnit.isEmpty()) {
            return ArgumentParseResult.failure(new AreaShopCommandException("setduration-wrongAmount", duration));
        }
        DurationInput durationInput = new DurationInput(durationInt, timeUnit.get());
        return ArgumentParseResult.success(durationInput);
    }
}
