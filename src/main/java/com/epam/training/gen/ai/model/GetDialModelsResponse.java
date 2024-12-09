package com.epam.training.gen.ai.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetDialModelsResponse {

  private List<ModelData> data;

  // Getters and setters
  public List<ModelData> getData() {
    return data;
  }

  public void setData(List<ModelData> data) {
    this.data = data;
  }


  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ModelData {

    private String model;

    // Getters and setters
    public String getModel() {
      return model;
    }

    public void setModel(String model) {
      this.model = model;
    }
  }
}
