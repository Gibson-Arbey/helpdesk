package co.model.league;

import co.model.exception.InvalidFieldException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class League {

    private final UUID id;
    private final String name;
    private final String description;
    private final Integer maximumNumberOfOpenTickets;
    private final Integer maximumNumberOfPoints;
    private final Integer minimumNumberOfPoints;

    private League(UUID id, String name, String description, Integer maximumNumberOfOpenTickets, Integer maximumNumberOfPoints, Integer minimumNumberOfPoints) {
        if (id == null) throw new InvalidFieldException("League id cannot be null");
        if (name == null || name.isBlank()) throw new InvalidFieldException("League name cannot be blank");
        if (maximumNumberOfOpenTickets == null || maximumNumberOfOpenTickets < 1) {
            throw new InvalidFieldException("Maximum open tickets must be positive");
        }
        if (maximumNumberOfPoints == null || maximumNumberOfPoints < 0) {
            throw new InvalidFieldException("Maximum points cannot be negative");
        }
        if (minimumNumberOfPoints == null || minimumNumberOfPoints < 0 || minimumNumberOfPoints > maximumNumberOfPoints) {
            throw new InvalidFieldException("Minimum points must be between zero and maximum points");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.maximumNumberOfOpenTickets = maximumNumberOfOpenTickets;
        this.maximumNumberOfPoints = maximumNumberOfPoints;
        this.minimumNumberOfPoints = minimumNumberOfPoints;
    }

    public static League restore(String name, String description, Integer maximumNumberOfOpenTickets, Integer maximumNumberOfPoints, Integer minimumNumberOfPoints) {
        return new League(UUID.randomUUID(), name, description, maximumNumberOfOpenTickets, maximumNumberOfPoints, minimumNumberOfPoints);
    }

    public static League restore(UUID id, String name, String description, Integer maximumNumberOfOpenTickets, Integer maximumNumberOfPoints, Integer minimumNumberOfPoints) {
        return new League(id, name, description, maximumNumberOfOpenTickets, maximumNumberOfPoints, minimumNumberOfPoints);
    }
}
