package com.example.getstock;

/**
 * This class represents a general user.
 */
public interface GeneralUser {
    /**
     * Return the user full name.
     * @return
     */
    public String getFullName();

    /**
     * Get user email, this is a Unique property.
     * @return
     */
    public String getEmail();

    /**
     * Get user age.
     * @return
     */
    public String getAge();

    /**
     * Get the user score.
     * @return
     */
    public String getUserScore();
}
