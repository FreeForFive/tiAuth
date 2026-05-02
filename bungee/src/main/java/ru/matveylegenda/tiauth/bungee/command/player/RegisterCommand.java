package ru.matveylegenda.tiauth.bungee.command.player;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.matveylegenda.tiauth.bungee.TiAuth;
import ru.matveylegenda.tiauth.bungee.manager.AuthManager;
import ru.matveylegenda.tiauth.bungee.storage.CachedMessages;
import ru.matveylegenda.tiauth.bungee.util.BungeeUtils;
import ru.matveylegenda.tiauth.config.MainConfig;

public class RegisterCommand extends Command {
    private final AuthManager authManager;

    public RegisterCommand(TiAuth plugin, String name, String... aliases) {
        super(name, null, aliases);
        this.authManager = plugin.getAuthManager();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer player)) {
            BungeeUtils.sendMessage(
                    sender,
                    CachedMessages.IMP.onlyPlayer
            );

            return;
        }

        boolean needRepeat = MainConfig.IMP.auth.repeatPasswordWhenRegister;
        int requiredArgs = needRepeat ? 2 : 1;

        if (args.length < requiredArgs) {
            BungeeUtils.sendMessage(player, CachedMessages.IMP.player.register.usage);
            return;
        }

        String password = args[0];
        String repeatPassword = needRepeat ? args[1] : args[0];

        authManager.registerPlayer(player, password, repeatPassword);
    }
}
