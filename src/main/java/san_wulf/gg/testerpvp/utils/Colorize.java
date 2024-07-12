package san_wulf.gg.testerpvp.utils;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colorize {
    @NotNull
    public static String a(String input) {
        if (input == null) {
            return "";
        }

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(input);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String colorCode = matcher.group();
            matcher.appendReplacement(sb, ChatColor.of(colorCode).toString());
        }

        matcher.appendTail(sb);
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }
}
