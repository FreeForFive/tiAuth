package ru.matveylegenda.tiauth.config;

import net.elytrium.serializer.SerializerConfig;
import net.elytrium.serializer.annotations.Comment;
import net.elytrium.serializer.annotations.CommentValue;
import net.elytrium.serializer.annotations.NewLine;
import net.elytrium.serializer.annotations.Transient;
import net.elytrium.serializer.language.object.YamlSerializable;
import ru.matveylegenda.tiauth.database.DatabaseType;
import ru.matveylegenda.tiauth.hash.HashType;
import ru.matveylegenda.tiauth.util.BarColor;
import ru.matveylegenda.tiauth.util.BarStyle;
import ru.matveylegenda.tiauth.util.colorizer.Serializer;

import java.nio.file.Paths;
import java.util.List;

public class MainConfig extends YamlSerializable {

    @Transient
    private static final SerializerConfig CONFIG = new SerializerConfig.Builder()
            .setCommentValueIndent(1)
            .build();

    @Transient
    public static final MainConfig IMP = new MainConfig();

    public MainConfig() {
        super(Paths.get("plugins/tiAuth/config.yml"), CONFIG);
        this.servers = new Servers();
        this.database = new Database();
        this.auth = new Auth();
        this.bossBar = new BossBar();
        this.title = new Title();
        this.actionBar = new ActionBar();
    }

    @Comment({
            @CommentValue("Available options:"),
            @CommentValue("LEGACY - \"&fText &#650dbdexample\""),
            @CommentValue("MINIMESSAGE - \"<white>Text</white> <color:#650dbd>example</color>\" (https://webui.advntr.dev/)")
    })
    public Serializer serializer = Serializer.LEGACY;

    @Comment({
            @CommentValue("Available languages: EN, RU")
    })
    public MessagesConfig.Lang lang = MessagesConfig.Lang.EN;

    public Servers servers;

    @NewLine
    public static class Servers {
        @Comment({
                @CommentValue("The auth server to which players will be moved for registration/authentication")
        })
        public String auth = "auth";

        @Comment({
                @CommentValue("The backend server to which players will be moved after registration/authentication")
        })
        public String backend = "hub";
    }

    public Database database;

    @NewLine
    public static class Database {
        @Comment({
                @CommentValue("Database type"),
                @CommentValue("Available options: SQLITE, H2, MYSQL, POSTGRESQL")
        })
        public DatabaseType type = DatabaseType.H2;
        public String host;
        public int port;
        public String database;
        public String user;
        public String password;

        @NewLine
        @Comment({
                @CommentValue("Connection pool settings (H2, MySQL, PostgreSQL)")
        })
        @Comment(
                value = @CommentValue("Maximum wait time for a connection from the pool"),
                at = Comment.At.SAME_LINE
        )
        public long connectionTimeoutMs = 30000;
        @Comment(
                value = @CommentValue("Maximum connection idle time in the pool. Applies only if min-idle is less than max-pool-size"),
                at = Comment.At.SAME_LINE
        )
        public long idleTimeoutMs = 600000;
        @Comment(
                value = @CommentValue("Maximum connection lifetime in the pool. After this, the connection will be closed and a new one opened if needed"),
                at = Comment.At.SAME_LINE
        )
        public long maxLifetimeMs = 1800000;
        @Comment(
                value = {
                        @CommentValue("Maximum number of connections in the pool"),
                        @CommentValue("For H2, it is recommended to use a small number of connections, e.g., 2"),
                        @CommentValue("For MySQL and PostgreSQL, you can set more, e.g., 10")
                },
                at = Comment.At.SAME_LINE
        )
        public int maxPoolSize = 2;
        @Comment(
                value = {
                        @CommentValue("Minimum number of idle connections in the pool. -1 = max-pool-size")
                },
                at = Comment.At.SAME_LINE
        )
        public int minIdle = -1;
    }

    public Auth auth;

    @NewLine
    public static class Auth {
        @Comment({
                @CommentValue("Number of password entry attempts")
        })
        public int loginAttempts = 3;

        @Comment({
                @CommentValue("Whether to ban the player after exhausting authentication attempts")
        })
        public boolean banPlayer = true;

        @Comment({
                @CommentValue("How many seconds to ban the player after exhausting authentication attempts")
        })
        public int banTime = 60;

        @Comment({
                @CommentValue("How often (in seconds) a reminder message to register/authenticate is sent to the player")
        })
        public int reminderInterval = 3;

        @Comment({
                @CommentValue("How many seconds the player is given to register/authenticate")
        })
        public int timeoutSeconds = 60;

        @Comment({
                @CommentValue("How long (in minutes) a player can join without re-authentication if their IP has not changed")
        })
        public int sessionLifetimeMinutes = 60;

        @Comment({
                @CommentValue("Minimum password length")
        })
        public int minPasswordLength = 6;

        @Comment({
                @CommentValue("Maximum password length")
        })
        public int maxPasswordLength = 32;

        @Comment({
                @CommentValue("Regular expression for the password")
        })
        public String passwordPattern = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*$";

        @Comment({
                @CommentValue("Password hashing algorithm"),
                @CommentValue("Available options:"),
                @CommentValue("BCRYPT"),
                @CommentValue("SHA256")
        })
        public HashType hashAlgorithm = HashType.BCRYPT;

        @Comment({
                @CommentValue("BCrypt algorithm complexity (cost)."),
                @CommentValue("The higher the value, the harder it is to brute-force the password."),
                @CommentValue("(Higher values result in more CPU load!)")
        })
        public int bcryptCost = 12;

        @Comment({
                @CommentValue("Commands that can be used during authentication")
        })
        public List<String> allowedCommands = List.of(
                "/login",
                "/log",
                "/l",
                "/register",
                "/reg"
        );

        @Comment({
                @CommentValue("Whether to use a dialog window for registration/authentication"),
                @CommentValue("Works only on clients 1.21.6+")
        })
        public boolean useDialogs = true;

        @Comment({
                @CommentValue("Whether the player needs to repeat the password in /register")
        })
        public boolean repeatPasswordWhenRegister = true;
    }

    public BossBar bossBar;

    @NewLine
    public static class BossBar {
        public boolean enabled = true;
        @Comment(
                value = @CommentValue("PINK / BLUE / RED / GREEN / YELLOW / PURPLE / WHITE"),
                at = Comment.At.SAME_LINE
        )
        public BarColor color = BarColor.PURPLE;
        @Comment(
                value = @CommentValue("SOLID / SEGMENTED_6 / SEGMENTED_10 / SEGMENTED_12 / SEGMENTED_20"),
                at = Comment.At.SAME_LINE
        )
        public BarStyle style = BarStyle.SEGMENTED_12;
    }

    public Title title;

    @NewLine
    public static class Title {
        public boolean enabled = false;
        public boolean enabledOnAuth = false;

        @Comment({
                @CommentValue("Fade settings (in ticks, 20 ticks = 1 second)")
        })
        public int fadeIn = 0;
        public int stay = 21;
        public int fadeOut = 0;

        @NewLine
        @Comment({
                @CommentValue("Fade settings after authentication (in ticks, 20 ticks = 1 second)")
        })
        public int onAuthFadeIn = 10;
        public int onAuthStay = 40;
        public int onAuthFadeOut = 10;

        @NewLine
        @Comment({
                @CommentValue("Delay before sending Title after authentication (in milliseconds)"),
                @CommentValue("Helps if the player doesn't see the Title due to teleportation to another server")
        })
        public long titleDelayMs = 1500;
    }

    public ActionBar actionBar;

    @NewLine
    public static class ActionBar {
        public boolean enabled = false;
    }

    @NewLine
    @Comment({
            @CommentValue("Regular expression for the nickname")
    })
    public String nickPattern = "^[a-zA-Z0-9_]{3,16}$";
    @Comment({
            @CommentValue("Maximum number of accounts playing simultaneously from one IP")
    })
    public int maxOnlineAccountsPerIp = 10;
    @Comment({
            @CommentValue("Maximum number of registered accounts from one IP")
    })
    public int maxRegisteredAccountsPerIp = 10;

    public List<String> excludedIps = List.of("127.0.0.1");

    public Libraries libraries = new Libraries();

    @NewLine
    public static class Libraries {
        public SQLite sqlite = new SQLite();

        public static class SQLite {
            public String version = "3.50.3.0";
        }

        public H2 h2 = new H2();

        public static class H2 {
            public String version = "2.3.232";
        }

        public MySQL mysql = new MySQL();

        public static class MySQL {
            public String version = "9.4.0";
        }

        public PostgreSQL postgresql = new PostgreSQL();

        public static class PostgreSQL {
            public String version = "42.7.7";
        }
    }
}
