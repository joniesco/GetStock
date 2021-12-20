package com.example.getstock;

/**
 * This is an interface for a callback function
 */
public interface VolleyCallback {
    /**
     * define what happens on success.
     * @param result
     */
    void onSuccess(String result);

    /**
     * define what happens on failure.
     * @param result
     */
    void onError(String result);
}
