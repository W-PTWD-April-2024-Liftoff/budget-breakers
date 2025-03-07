package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public abstract class AbstractEntity extends BaseAbstractEntity{

    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Size(min = 0 , max = 250, message = "Description must be less than 250 characters")
    private String description;

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

}
