package ru.arhipov.url_db_rest_max.entity;

public enum Role {
    READ_ONLY("READ_ONLY"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;
    Role(String name){this.name=name;}
    public String getName(){return name;}

    public static Role fromString(String name){
        for (Role role:Role.values()){
            if(role.name.equalsIgnoreCase(name)){return role;}}
        throw new IllegalArgumentException("Не опознан 'role' "+name);}
}
