import axios from 'axios';

axios.interceptors.request.use(config => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.token) {
        const token = 'Bearer ' + user.token;
        config.headers.Authorization = token;
    }
    return config;
});
class AdminService {
    async getCustomerByEmail(email) {
        return await axios.get('http://localhost:8080/admin/get-customer-by-email/' + email)
    }
    async getCompanyByEmail(email) {
        return await axios.get('http://localhost:8080/admin/get-company-by-email/' + email)
    }
    async getCoupons() {
        return await axios.get('http://localhost:8080/admin/get-all-coupons')
    }
    async getOneCustomerByCustomerId(customerId) {
        return await axios.get('http://localhost:8080/admin/get-one-customer-by-customerId/' + customerId)
    }
    async getAllCustomers() {
        return await axios.get('http://localhost:8080/admin/get-all-customers')
    }
    async deleteCustomer(customerId) {
        return await axios.delete('http://localhost:8080/admin/delete-customer/' + customerId)
    }
    async updateCustomer(id, firstName, lastName, email, password) {
        return await axios.put('http://localhost:8080/admin/update-customer', {
            id, firstName, lastName, email, password
        })
    }
    async addCustomer(firstName, lastName, email, password) {
        return await axios.post('http://localhost:8080/admin/add-customer', {
            firstName, lastName, email, password
        })
    }
    async getOneCompanyByCompanyId(companyId) {
        return await axios.get('http://localhost:8080/admin/get-one-company-by-companyId/' + companyId)
    }
    async getAllCompanies() {
        return await axios.get('http://localhost:8080/admin/get-all-companies')
    }
    async deleteCompany(companyId) {
        return await axios.delete('http://localhost:8080/admin/delete-company/' + companyId)
    }
    async updateCompany(id, name, email, password) {
        return await axios.put('http://localhost:8080/admin/update-company', {
            id, name, email, password
        })
    }
    async addCompany(name, email, password) {
        return await axios.post('http://localhost:8080/admin/add-company', {
            name, email, password
        })
    }

    async getLogs() {
        return await axios.get('http://localhost:8080/admin/get-logs')
    }

    async clearLogs() {
        return await axios.delete('http://localhost:8080/admin/clear-logs')
    }
}
export default new AdminService();

