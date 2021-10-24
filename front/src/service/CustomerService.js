import axios from "axios";

axios.interceptors.request.use((config) => {
  const user = JSON.parse(localStorage.getItem('user'));
  if (user && user.token) {
    const token = 'Bearer ' + user.token;
    config.headers.Authorization = token;
  }
  return config;
});
class CustomerService {
  async getCustomerDetails() {
    return await axios.get("http://localhost:8080/customer/get-customer-details");
  }
  async purchaseCoupon(couponId) {
    return await axios.put(
      "http://localhost:8080/customer/purchase-coupon/" + couponId
    );
  }
  async getCustomerCoupons() {
    return await axios.get(
      "http://localhost:8080/customer/get-customer-coupons"
    );
  }
  async getCustomerCouponsByCategory(category) {
    return await axios.get(
      "http://localhost:8080/customer/get-customer-coupons-by-category/" +
      category
    );
  }
  async getCustomerCouponsByMaxPrice(maxPrice) {
    return await axios.get(
      "http://localhost:8080/customer/get-customer-coupons-by-max-price/" +
      maxPrice
    );
  }
}
export default new CustomerService();
