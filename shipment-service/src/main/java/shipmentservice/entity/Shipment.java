package shipmentservice.entity;

import lombok.Data;

@Data
// @Entity
public class Shipment {

  private Long id;
  private String origin;
  private String destination;
  private String status;


}
