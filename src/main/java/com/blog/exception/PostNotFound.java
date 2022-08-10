package com.blog.exception;

/**
 * 정책상 status -> 404
 */
public class PostNotFound extends HodologException{


    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getstatusCode() {
        return 404;
    }
}
