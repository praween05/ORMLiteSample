package com.sample.ormlite.controller;

import android.app.Activity;
import android.content.Context;

/**
 * Created by praween on 2/5/16.
 */
public class EventController {
    public final Context mContext;
    private static EventController sEventController;
    public boolean shutdown;

    public EventController(Context context) {
            mContext = context;
    }

    public static class Builder{
        private final Context context;

        /** Start building a new {@link EventController} instance. */
        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        /** Create the {@link EventController} instance. */
        public EventController build() {
            return new EventController(context);
        }
    }

    /**
     * method which return {@link EventController}
     * @param context
     * @return
     */
    public static EventController with(Context context) {
        if (sEventController == null) {
            synchronized (EventController.class) {
                if (sEventController == null) {
                    sEventController = new Builder(context).build();
                }
            }
        }
        return sEventController;
    }

    public RequestHandler start(Class targetClass) {
        return new RequestHandler(sEventController,0,targetClass);
    }
}

