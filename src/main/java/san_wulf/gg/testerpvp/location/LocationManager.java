package san_wulf.gg.testerpvp.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import san_wulf.gg.testerpvp.config.LocationConfigManager;

import java.util.Random;
import java.util.Set;

public class LocationManager {
    private static LocationConfigManager locmanager;
    private static final Random random = new Random();
    private static Set<String> avoidBlocks;

    public LocationManager(LocationConfigManager locmanager) {
        this.locmanager = locmanager;
        this.avoidBlocks = locmanager.getAvoidBlocks();
    }
    public static Location getRandomSurfaceLocation() {
        World world = Bukkit.getWorld(locmanager.getWorld());
        if (world == null) return null;

        int maxAttempts = locmanager.getMaxAttempts();
        int rangeX = locmanager.getRangeX();
        int rangeZ = locmanager.getRangeZ();

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int x = random.nextInt(rangeX * 2) - rangeX;
            int z = random.nextInt(rangeZ * 2) - rangeZ;
            int y = world.getHighestBlockYAt(x, z);

            Location location = new Location(world, x, y, z);
            if (!avoidBlocks.contains(location.getBlock().getType())) {
                return location;
            }
        }

        return null;
    }
}
