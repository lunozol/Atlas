package io.lunozol.atlas.system.features.module.property;

public abstract class Property {
    public abstract void require(boolean requirement);

    public abstract boolean isHidden();
}
