package com.aetherteam.nitrogen.data.providers;

import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;

import java.util.function.Function;

/**
 * A {@link TagAppender} that can also accept registry objects directly, converting them to their {@link ResourceKey}s.
 *
 * @param <T> The type of the registry object.
 */
public class IntrinsicTagAppender<T> implements TagAppender<T> {
    private final TagAppender<T> delegate;
    private final Function<T, ResourceKey<T>> keyExtractor;

    public IntrinsicTagAppender(TagAppender<T> delegate, Function<T, ResourceKey<T>> keyExtractor) {
        this.delegate = delegate;
        this.keyExtractor = keyExtractor;
    }

    public IntrinsicTagAppender<T> add(T element) {
        this.delegate.add(this.keyExtractor.apply(element));
        return this;
    }

    @SafeVarargs
    public final IntrinsicTagAppender<T> add(T... elements) {
        for (T element : elements) {
            this.add(element);
        }
        return this;
    }

    @Override
    public IntrinsicTagAppender<T> add(ResourceKey<T> element) {
        this.delegate.add(element);
        return this;
    }

    @Override
    public IntrinsicTagAppender<T> addOptional(ResourceKey<T> element) {
        this.delegate.addOptional(element);
        return this;
    }

    @Override
    public IntrinsicTagAppender<T> addTag(TagKey<T> tag) {
        this.delegate.addTag(tag);
        return this;
    }

    @SafeVarargs
    public final IntrinsicTagAppender<T> addTags(TagKey<T>... tags) {
        for (TagKey<T> tag : tags) {
            this.delegate.addTag(tag);
        }
        return this;
    }

    @Override
    public IntrinsicTagAppender<T> addOptionalTag(TagKey<T> tag) {
        this.delegate.addOptionalTag(tag);
        return this;
    }

}
