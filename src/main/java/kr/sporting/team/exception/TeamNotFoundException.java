package kr.sporting.team.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("Team not found with the provided ID.");
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}