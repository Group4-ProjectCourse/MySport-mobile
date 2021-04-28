package com.mysport.mysport_mobile.forum;

import android.widget.EditText;

import static org.junit.jupiter.api.Assertions.*;

class CreatePostTest {


    private EditText postTitle, postContent;
    private String username;

    @org.junit.jupiter.api.Test
    void createPost() {
        String expectedTitle = "Greetings";
        String expectedContent = "Sup guys";
        String expectedAuthor = "Tom Nillson";

        String actualTitle = CreatePost.postTitle.getText().toString();
        String actualContent = CreatePost.postContent.getText().toString();
        String actualAuthor = CreatePost.username;

        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedContent, actualContent);
        assertEquals(expectedAuthor, actualAuthor);
    }
}