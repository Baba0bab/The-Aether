package com.aetherteam.aether.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class GoldenOakLeavesParticle extends PortalParticle {
    public GoldenOakLeavesParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite sprite) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, sprite);
        this.rCol = 0.976F;
        this.gCol = 0.745F;
        this.bCol = 0.0F;
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new GoldenOakLeavesParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet().get(random));
        }
    }
}
