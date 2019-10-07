package com.example;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Component(modules = BindsProviderLazyCycle.Module1.class)
public interface BindsProviderLazyCycle {
    TestFactory getFactory();

    class TestClass {
        @Inject TestClass(TestFactory factory) { }
    }

    class TestFactory {
        private Provider<Object> provider;

        @Inject
        TestFactory(Map<Class<?>, Provider<Object>> providers) {
            this.provider = providers.get(TestClass.class);
        }

        public TestClass createClass() {
            return (TestClass) provider.get();
        }
    }

    @Module
    abstract class Module1 {
        @Binds
        @IntoMap
        @ClassKey(TestClass.class)
        abstract Object bindsClass(TestClass nothing);
    }
}
