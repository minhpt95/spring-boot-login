package com.catdev.project.constant;

public class ErrorConstant {
    public class Code{
        public static final String SUCCESS = "00";
        public static final String LOGIN_INVALID = "01";
        public static final String USER_INACTIVE = "02";
        public static final String NOT_FOUND = "03";
        public static final String ALREADY_EXISTS = "04";
        public static final String PERMISSION_DENIED = "05";
    }

    public class Type{
        public static final String LOGIN_INVALID = "LOGIN_INVALID";
        public static final String USER_INACTIVE = "USER_INACTIVE";
        public static final String SUCCESS = "SUCCESS";
        public static final String FAILURE = "FAILURE";
        public static final String TOKEN_INVALID = "TOKEN_INVALID";
        public static final String ALREADY_EXISTS = "ALREADY_EXISTS";
    }

    public class MessageEN{
        public static final String LOGIN_INVALID = "Username or password invalid.";
        public static final String USER_INACTIVE = "User inactive.";
        public static final String SUCCESS = "SUCCESS.";
        public static final String ALREADY_EXISTS = "%s already exists.";
        public static final String NOT_EXISTS = "%s not exists.";
        public static final String BLANK =" %s not blank.";
        public static final String END_OF_TIME ="end of time.";
        public static final String ALREADY_EXISTS_BRANCH = "Already exists branch or department.";
        public static final String USER_EXISTS ="User exists organization";
    }

    public class MessageVI{
        public static final String LOGIN_INVALID = "Thông tin đăng nhập không đúng.";
        public static final String USER_INACTIVE = "Người dùng đang bị khoá.";
        public static final String SUCCESS = "Thành công.";
        public static final String ALREADY_EXISTS = "%s đã tồn tại.";
        public static final String NOT_EXISTS = "%s không tồn tại.";
        public static final String  BLANK ="%s không được bỏ trống.";
        public static final String END_OF_TIME ="Hết thời gian.";
        public static final String ALREADY_EXISTS_BRANCH = "Đã tồn tại chi nhánh hoặc phòng ban.";
        public static final String USER_EXISTS ="Người dùng tồn tại tổ chức.";
    }
}
