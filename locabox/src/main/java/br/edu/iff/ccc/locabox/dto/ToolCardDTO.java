package br.edu.iff.ccc.locabox.dto;

import java.math.BigDecimal;

public class ToolCardDTO {
    private Long id;
    private String title;
    private String mainImageUrl;
    private BigDecimal pricePerDay;
    private Double rating;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMainImageUrl() { return mainImageUrl; }
    public void setMainImageUrl(String mainImageUrl) { this.mainImageUrl = mainImageUrl; }
    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
