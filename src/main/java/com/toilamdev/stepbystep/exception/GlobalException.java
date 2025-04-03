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

    public static class NotCreateRolesException extends RuntimeException{
        public NotCreateRolesException(String message){
            super(message);
        }
    }

    public static class CategoryNotFoundException extends RuntimeException{
        public CategoryNotFoundException(String message){
            super(message);
        }
    }

    public static class TagNotFoundException extends RuntimeException{
        public TagNotFoundException(String message){
            super(message);
        }
    }

    public static class CourseNotFoundException extends RuntimeException{
        public CourseNotFoundException(String message){
            super(message);
        }
    }

    public static class SectionNotFoundException extends RuntimeException{
        public SectionNotFoundException(String message){
            super(message);
        }
    }
}
