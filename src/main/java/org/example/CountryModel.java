package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryModel {
    private String name;
    private String alpha2Code;
    private String alpha3Code;
    private String[] callingCodes;
    private String subregion;
    private String region;
    private String population;
    private String demonym;
    private String area;
    private String[] timezones;
    private String nativeName;
    private String numericCode;
    private String flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String[] getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(String[] callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public void setTimezones(String[] timezones) {
        this.timezones = timezones;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "CountryModel{" +
                "name='" + name + '\'' +
                ", alpha2Code='" + alpha2Code + '\'' +
                ", alpha3Code='" + alpha3Code + '\'' +
                ", callingCodes=" + Arrays.toString(callingCodes) +
                ", subregion='" + subregion + '\'' +
                ", region='" + region + '\'' +
                ", population='" + population + '\'' +
                ", demonym='" + demonym + '\'' +
                ", area='" + area + '\'' +
                ", timezones=" + Arrays.toString(timezones) +
                ", nativeName='" + nativeName + '\'' +
                ", numericCode='" + numericCode + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
