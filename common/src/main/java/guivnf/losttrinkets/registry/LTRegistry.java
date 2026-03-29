package guivnf.losttrinkets.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class LTRegistry<T> {
    private final DeferredRegister<T> delegate;

    private LTRegistry(DeferredRegister<T> delegate) {
        this.delegate = delegate;
    }

    public static <T> LTRegistry<T> of(DeferredRegister<T> register) {
        return new LTRegistry<>(register);
    }

    public static <T> LTRegistry<T> create(ResourceKey<Registry<T>> registryKey, String modId) {
        return new LTRegistry<>(DeferredRegister.create(modId, registryKey));
    }

    public <V extends T> RegistrySupplier<V> register(String name, Supplier<V> supplier) {
        return delegate.register(name, supplier);
    }

    public void init() {
        delegate.register();
    }
}
