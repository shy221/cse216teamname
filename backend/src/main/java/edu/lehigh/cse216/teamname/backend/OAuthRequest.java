package edu.lehigh.cse216.teamname.backend;

/**
 * OAuth request provides a format to store JSON returned 
 * by Google OAuth Server. All fields must be set to match
 * the response from Google.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */

 public class OAuthRequest {
    /**
     * The short-lifed access token returned from Google
     */
    public String access_token;

    /**
     * The remaining lifetime of the access token in seconds
     */
    public int expires_in;

    /**
     * Token type. Accoring to Google's documentation, for now
     * it would always be 'Bearer'
     */
    public String token_type;
 }