//package com.aetherteam.aether.data.generators.tags;
//
//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.AetherTags;
//import com.aetherteam.aether.data.resources.registries.AetherTimelines;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.tags.TagsProvider;
//import net.minecraft.tags.TimelineTags;
//import net.minecraft.world.timeline.Timeline;
//
//import java.util.concurrent.CompletableFuture;
//
///**
// * Timeline tags determine which timelines are active in a dimension.
// */
//public class AetherTimelineTagData extends TagsProvider<Timeline> {
//    public AetherTimelineTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
//        super(output, Registries.TIMELINE, registries, Aether.MODID);
//    }
//
//    @Override
//    protected void addTags(HolderLookup.Provider provider) {
//        this.tag(AetherTags.Timelines.IN_AETHER).addTag(TimelineTags.UNIVERSAL).add(AetherTimelines.DAY, AetherTimelines.MOON);
//    }
//}
