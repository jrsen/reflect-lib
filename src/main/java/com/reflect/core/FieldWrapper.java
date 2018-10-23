package com.reflect.core;

public final class FieldWrapper {

    private Field<Object> mField;
    private Object mObject;

    FieldWrapper(Object object) {
        this.mObject = object;
    }

    public FieldWrapper getField(String name) {
        try {
            if (mField != null) mObject = mField.getUnsafe(mObject);
            for (Class<?> clazz = mObject.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    mField = new Field<>(clazz.getDeclaredField(name));
                    return this;
                } catch (NoSuchFieldException ignore) {
//                    ignore.printStackTrace();
                }
            }
            throw new NoSuchFieldException();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public Object getObject() {
        return mField == null ? mObject : mField.get(mObject);
    }

    public void setObject(Object obj) {
        if (mField == null) {
            throw new UnsupportedOperationException("not found field to set obj!");
        } else {
            mField.set(this.mObject, obj);
        }
    }
}
