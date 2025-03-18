package com.toilamdev.stepbystep.exception;

public class GlobalException {
    public static class RoleNotFoundException extends RuntimeException {
        public RoleNotFoundException(String message) {
            super(message);
        }
    }

    public static class JwtGenerationException extends RuntimeException{
        public JwtGenerationException(String message){
            super(message);
        }
    }
}
