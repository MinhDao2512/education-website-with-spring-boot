package com.toilamdev.stepbystep.constant;

public class ApiResponseExample {
    public static final String OK_200 = """
            {
              "success": true,
              "status": 200,
              "timestamp": "2025-02-27T14:43:40.144Z",
              "message": "Get data successfully",
              "data":[ 
                  {
                      "field_name": "value"
                  }
              ]
            }
            """;

    public static final String CREATED_201 = """
            {
              "success": true,
              "status": 201,
              "timestamp": "2025-02-27T14:43:40.144Z",
              "message": "Create successfully",
              "data":[ 
                  {
                      "field_name": "value"
                  }
              ]
            }
            """;

    public static final String NO_CONTENT_204 = """
            {
              "success": true,
              "status": 204,
              "timestamp": "2025-02-27T14:43:40.144Z",
              "message": "Do something successfully"
            }
            """;

    public static final String BAD_REQUEST_400 = """
            {
              "success": false,
              "status": 400,
              "timestamp": "2025-02-27T14:43:40.144Z",
              "message": "Parameters is invalid",
              "path": "/api/v1/...",
              "errors": [
                  {
                      "field_error_name": "error_message"
                  }
              ]
            }
            """;

    public static final String UNAUTHORIZED_401 = """
            {
              "success": false,
              "status": 401,
              "timestamp": "2025-02-27T14:43:40.144Z",
              "message": "Unauthorized",
              "path": "/api/v1/...",
              "errors": [
                  {
                      "field_error_name": "error_message"
                  }
              ]
            }
            """;
}
