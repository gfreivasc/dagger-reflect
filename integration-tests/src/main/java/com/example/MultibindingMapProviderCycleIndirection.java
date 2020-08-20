package com.example;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Component(modules = MultibindingMapProviderCycleIndirection.Module1.class)
public interface MultibindingMapProviderCycleIndirection {
  Factory factory();

  @Module
  abstract class Module1 {
    @Binds
    @IntoMap
    @ClassKey(A.class)
    abstract Object bindA(A a);
  }

  class A {
    public final Factory factory;

    @Inject
    A(Factory factory) {
      this.factory = factory;
    }
  }

  class Factory {
    public final Map<Class<?>, Provider<Object>> providerMap;

    @Inject
    Factory(Map<Class<?>, Provider<Object>> providerMap) {
      this.providerMap = providerMap;
    }
  }
}
