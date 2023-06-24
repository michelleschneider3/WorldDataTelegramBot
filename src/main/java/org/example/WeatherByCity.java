package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherByCity {
    private Map<String, Object> coord;
    private Map<String, Object>[] weather;
    private String base;
    private Map<String, Object> main;
    private int visibility;
    private Map<String, Object> wind;
    private Map<String, Integer> clouds;
    private long dt;
    private Map<String, Object> sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;


    public Map<String, Object> getCoord() {
        return coord;
    }

    public void setCoord(Map<String, Object> coord) {
        this.coord = coord;
    }

    public Map<String, Object>[] getWeather() {
        return weather;
    }

    public void setWeather(Map<String, Object>[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Object> getMain() {
        return main;
    }

    public void setMain(Map<String, Object> main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Map<String, Object> getWind() {
        return wind;
    }

    public void setWind(Map<String, Object> wind) {
        this.wind = wind;
    }

    public Map<String, Integer> getClouds() {
        return clouds;
    }

    public void setClouds(Map<String, Integer> clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Map<String, Object> getSys() {
        return sys;
    }

    public void setSys(Map<String, Object> sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }


}
