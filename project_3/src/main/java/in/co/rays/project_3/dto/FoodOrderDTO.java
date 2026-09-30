package in.co.rays.project_3.dto;

public class FoodOrderDTO extends BaseDTO {

    private int orderId;
    private String customerName;
    private String restaurant;
    private long orderAmount;
    private String deliveryStatus;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

   
    }