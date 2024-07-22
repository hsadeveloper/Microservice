package store.service.entity;

public class ProductMapper {
    public static ProductDTO toProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setProductPrice(product.getProductPrice());
        dto.setDepartmentId(product.getDepartment() != null ? product.getDepartment().getDepartmentId() : null);
        dto.setInventoryId(product.getInventory() != null ? product.getInventory().getInventoryId() : null);
        dto.setSerialNumber(product.getSerialNumber());
        return dto;
    }
}