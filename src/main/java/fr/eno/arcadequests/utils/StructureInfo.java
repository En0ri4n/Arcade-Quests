package fr.eno.arcadequests.utils;

import org.bukkit.Material;

public enum StructureInfo
{
    BASE(0L, Material.COBBLESTONE, Material.OAK_PLANKS, Material.GRANITE, Material.TORCH);

    private final long level;
    private final Material base;
    private final Material corners;
    private final Material top;
    private final Material cornersDeco;

    StructureInfo(long level, Material base, Material corners, Material top, Material cornersDeco)
    {
        this.level = level;
        this.base = base;
        this.corners = corners;
        this.top = top;
        this.cornersDeco = cornersDeco;
    }

    public long getLevel()
    {
        return level;
    }

    public Material getBase()
    {
        return base;
    }

    public Material getCorners()
    {
        return corners;
    }

    public Material getTop()
    {
        return top;
    }

    public Material getCornersDeco()
    {
        return cornersDeco;
    }
}