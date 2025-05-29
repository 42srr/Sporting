package kr.sporting.exception;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
