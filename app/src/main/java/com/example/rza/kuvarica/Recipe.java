package com.example.rza.kuvarica;

/**
 * Created by rza on 9/16/17.
 */

public class Recipe {
    String name;
    String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recipe{");
        sb.append("name='").append(name).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
