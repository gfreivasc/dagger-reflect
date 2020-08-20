package com.example;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Component(modules = MultibindingMapProviderCycleIndirection.Module1.class)
public interface MultibindingMapProviderCycleIndirection {
  Factory factory();

  @Module
  abstract class Module1 {
    @Provides
    @IntoMap
    @StringKey("1")
    static Long one(Factory factory) { return 1L; }
  }

  class Factory {
    public final Map<String, Provider<Long>> providerMap;

    @Inject
    Factory(Map<String, Provider<Long>> providerMap) {
      this.providerMap = providerMap;
    }
  }
}
