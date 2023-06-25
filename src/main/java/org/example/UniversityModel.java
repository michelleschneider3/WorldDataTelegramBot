package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UniversityModel {
    private String country;
    private String alpha_two_code;
    private String name;
    private String[] web_pages;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAlpha_two_code() {
        return alpha_two_code;
    }

    public void setAlpha_two_code(String alpha_two_code) {
        this.alpha_two_code = alpha_two_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getWeb_pages() {
        return web_pages;
    }

    public void setWeb_pages(String[] web_pages) {
        this.web_pages = web_pages;
    }

    @Override
    public String toString() {
        return "UniversityModel{" +
                "country='" + country + '\'' +
                ", alpha_two_code='" + alpha_two_code + '\'' +
                ", name='" + name + '\'' +
                ", web_pages=" + Arrays.toString(web_pages) +
                '}';
    }
}
