package dev.kofeychi.osc;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.function.Consumer;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Osc implements ModInitializer {
    @SafeVarargs
    private static LiteralArgumentBuilder<ServerCommandSource> literalThen(String lit, ArgumentBuilder<ServerCommandSource, ?>...arguments) {
        var lite = literal(lit);
        for (ArgumentBuilder<ServerCommandSource, ?> argument : arguments)
            lite = lite.then(argument);
        return lite;
    }
    private static LiteralArgumentBuilder<ServerCommandSource> literalExc(String lit, Command<ServerCommandSource> cmd) {
        var lite = literal(lit);
        return lite.executes(cmd);
    }

    private static LiteralArgumentBuilder<ServerCommandSource>
    cmdosc(){
        return literalThen(
                "osc",
                literalExc(
                        "reload",
                        ctx -> {
                            OscConfig.HOLDER.applyChanges();
                            return 1;
                        }
                ),
                literalExc(
                        "load",
                        ctx -> {
                            OscConfig.HOLDER.load();
                            return 1;
                        }
                ),
                literalThen(
                        "cfgnuke",
                        literalThen(
                                "setRings",
                                argument(
                                        "rings",
                                        IntegerArgumentType.integer(1,12)
                                ).executes(
                                        ctx -> {
                                            OscConfig.HOLDER.instance().nuke.RINGS = IntegerArgumentType.getInteger(ctx, "rings");
                                            OscConfig.HOLDER.applyChanges();
                                            return 1;
                                        }
                                )
                        ),
                        literalThen(
                                "setRingStep",
                                argument(
                                        "ringstep",
                                        IntegerArgumentType.integer(1,16)
                                ).executes(
                                        ctx -> {
                                            OscConfig.HOLDER.instance().nuke.RING_STEP = IntegerArgumentType.getInteger(ctx, "ringstep");
                                            OscConfig.HOLDER.applyChanges();
                                            return 1;
                                        }
                                )
                        ),
                        literalThen(
                                "setTntPerRing",
                                argument(
                                        "tnt",
                                        IntegerArgumentType.integer(1,16)
                                ).executes(
                                        ctx -> {
                                            OscConfig.HOLDER.instance().nuke.TNT_PER_RING = IntegerArgumentType.getInteger(ctx, "tnt");
                                            OscConfig.HOLDER.applyChanges();
                                            return 1;
                                        }
                                )
                        ),
                        literalThen(
                                "setTntPerRingAdded",
                                argument(
                                        "tnt",
                                        IntegerArgumentType.integer(1,16)
                                ).executes(
                                        ctx -> {
                                            OscConfig.HOLDER.instance().nuke.TNT_PER_RING_ADDED = IntegerArgumentType.getInteger(ctx, "tnt");
                                            OscConfig.HOLDER.applyChanges();
                                            return 1;
                                        }
                                )
                        )

                ),
                literalThen(
                        "cfgstab",
                        literalThen(
                                "setDepth",
                                argument(
                                        "depth",
                                        IntegerArgumentType.integer(1,32)
                                ).executes(
                                        ctx -> {
                                            OscConfig.HOLDER.instance().stab.DEPTH = IntegerArgumentType.getInteger(ctx, "depth");
                                            OscConfig.HOLDER.applyChanges();
                                            return 1;
                                        }
                                )
                        ),
                        literalThen(
                                "setSpacing",
                                argument(
                                        "spacing",
                                        IntegerArgumentType.integer(1,16)
                                ).executes(
                                        ctx -> {
                                            OscConfig.HOLDER.instance().stab.SPACING = IntegerArgumentType.getInteger(ctx, "spacing");
                                            OscConfig.HOLDER.applyChanges();
                                            return 1;
                                        }
                                )
                        )

                )
        );
    }

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, accs,env) -> {
            dispatcher.register(cmdosc());
        });
        OscConfig.HOLDER.load();
    }
}
