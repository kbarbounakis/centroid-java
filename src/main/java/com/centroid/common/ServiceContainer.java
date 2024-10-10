package com.centroid.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ServiceContainer {

    private static ServiceContainer instance = null;
    protected java.util.HashMap<String, Object> services = new java.util.HashMap<String, Object>();

    public ServiceContainer() {
    }

    public static ServiceContainer getInstance() {
        if (instance == null) {
            instance = new ServiceContainer();
        }
        return instance;
    }

    public <T> void useService(java.lang.Class<T> serviceClass, T service) {
        this.services.put(serviceClass.getName(), service);
    }

    public <T> void useService(java.lang.Class<T> serviceClass, java.lang.Class<T> strategyClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor<?> ctor = strategyClass.getConstructor(ServiceContainer.class);
        if (ctor != null) {
            this.services.put(serviceClass.getName(), ctor.newInstance(this));
            return;
        }
        // get paramterless constructor
        ctor = strategyClass.getConstructor();
        if (ctor == null) {
            throw new InstantiationException("The given strategy does not have a parameterless constructor.");
        }
        this.services.put(serviceClass.getName(), ctor.newInstance(this));
        return;
    }

    public <T> T getService(java.lang.Class<T> serviceClass) {
        // Get the service
        Object service = this.services.get(serviceClass.getName());
        if (service == null) {
            return null;
        }
        return serviceClass.cast(service);
    }

    public <T> void removeService(java.lang.Class<T> serviceClass) {
        // Remove the service
        this.services.remove(serviceClass.getName());
    }

}
