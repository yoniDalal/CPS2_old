import axios from 'axios';

axios.interceptors.request.use(config => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.token) {
        const token = 'Bearer ' + user.token;
        config.headers.Authorization = token;
    }
    return config;
});
class CompanyService {
    async getCompanyDetails() {
        return await axios.get('http://localhost:8080/company/get-company-details')
    }
    async getCompanyCouponsByMaxPrice(maxPrice) {
        return await axios.get('http://localhost:8080/company/get-company-coupons-by-max-price/' + maxPrice)
    }
    async getCompanyCouponsByCategory(category) {
        return await axios.get('http://localhost:8080/company/get-company-coupons-by-category/' + category)
    }
    async getCompanyCoupons() {
        return await axios.get('http://localhost:8080/company/get-company-coupons')
    }
    async deleteCompanyCoupon(couponId) {
        return await axios.delete('http://localhost:8080/company/delete-company-coupon/' + couponId)
    }
    async updateCompanyCoupon(id, category, title, description, startDate, endDate, amount, price, image) {
        return await axios.put('http://localhost:8080/company/update-company-coupon', {
            id, category, title, description, startDate, endDate, amount, price, image
        })
    }
    async addCompanyCoupon(category, title, description, startDate, endDate, amount, price, image) {
        return await axios.post('http://localhost:8080/company/add-company-coupon', {
            category, title, description, startDate, endDate, amount, price, image
        })
    }
}
export default new CompanyService();