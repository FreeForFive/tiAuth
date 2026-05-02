package ru.matveylegenda.tiauth.velocity.command.player;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import ru.matveylegenda.tiauth.config.MainConfig;
import ru.matveylegenda.tiauth.velocity.TiAuth;
import ru.matveylegenda.tiauth.velocity.manager.AuthManager;
import ru.matveylegenda.tiauth.velocity.storage.CachedComponents;
import ru.matveylegenda.tiauth.velocity.util.VelocityUtils;

public class RegisterCommand implements SimpleCommand {
    private final AuthManager authManager;

    public RegisterCommand(TiAuth plugin) {
        this.authManager = plugin.getAuthManager();
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();

        if (!(sender instanceof Player player)) {
            VelocityUtils.sendMessage(sender, CachedComponents.IMP.onlyPlayer);
            return;
        }

        boolean needRepeat = MainConfig.IMP.auth.repeatPasswordWhenRegister;
        int requiredArgs = needRepeat ? 2 : 1;

        if (args.length < requiredArgs) {
            VelocityUtils.sendMessage(player, CachedComponents.IMP.player.register.usage);
            return;
        }

        String password = args[0];
        String repeatPassword = needRepeat ? args[1] : args[0];

        authManager.registerPlayer(player, password, repeatPassword);
    }
}