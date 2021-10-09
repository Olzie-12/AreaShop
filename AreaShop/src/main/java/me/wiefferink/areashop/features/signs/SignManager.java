package me.wiefferink.areashop.features.signs;

import io.github.bakedlibs.dough.blocks.BlockPosition;
import me.wiefferink.areashop.managers.Manager;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SignManager extends Manager {

    private final Map<World, SignCache> signCacheMap = new HashMap<>();

    public Collection<BlockPosition> allSignLocations() {
        final Set<BlockPosition> set = new HashSet<>(signCacheMap.size());
        for (SignCache signCache : signCacheMap.values()) {
            set.addAll(signCache.allSignLocations());
        }
        return set;
    }

    public Collection<RegionSign> allSigns() {
        final Set<RegionSign> signs = new HashSet<>(signCacheMap.size());
        for (SignCache signCache : signCacheMap.values()) {
            signs.addAll(signCache.allSigns());
        }
        return signs;
    }

    public SignCache cacheForWorld(World world) {
        return this.signCacheMap.computeIfAbsent(world, x -> new SignCache());
    }

    public Optional<SignCache> getCacheForWorld(World world) {
        return Optional.ofNullable(this.signCacheMap.get(world));
    }

    public Optional<RegionSign> signFromLocation(Location location) {
        final World world = location.getWorld();
        return getCacheForWorld(world)
                .flatMap(cache -> cache.signAtLocation(BlockPosition.getAsLong(location)));
    }

    public void addSign(RegionSign regionSign) {
        Location location = regionSign.getLocation();
        cacheForWorld(location.getWorld()).addSign(regionSign);
    }

    public void removeSign(RegionSign regionSign) {
        removeSign(regionSign.getLocation());
    }

    public Optional<RegionSign> removeSign(Location location) {
        return getCacheForWorld(location.getWorld()).flatMap(cache -> cache.removeSign(location));
    }

    /**
     * Update all signs connected to this region.
     * @return true if all signs are updated correctly, false if one or more updates failed
     */
    public boolean update() {
        boolean result = true;
        for (SignCache signCache : this.signCacheMap.values())
        for(RegionSign sign : signCache.allSigns()) {
            result &= sign.update();
        }
        return result;
    }

    /**
     * Check if any of the signs need periodic updating.
     * @return true if one or more of the signs need periodic updating, otherwise false
     */
    public boolean needsPeriodicUpdate() {
        boolean result = false;
        for (SignCache signCache : this.signCacheMap.values()) {
            for (RegionSign sign : signCache.allSigns()) {
                if (result) {
                    break;
                }
                if (sign.needsPeriodicUpdate()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }


    public void shutdown() {
        this.signCacheMap.values().forEach(SignCache::clear);
        this.signCacheMap.clear();
    }

}