import axios from 'axios';
import history from './../App/Components/history'

class AutenticationService {

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

    login = async (email, password, type) => {
        return await axios.post('http://localhost:8080/login/login/' + email + '/' + password + '/' + type)
            .then(response => {
                if (response.data) {
                    localStorage.setItem("user", JSON.stringify(response.data))
                }
                return response.data

            })
            .catch(err => { throw err })
    }

    logOut() {
        localStorage.removeItem("user");
        history.push('/login');
    }
}

export default new AutenticationService()
