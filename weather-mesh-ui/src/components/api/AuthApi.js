import axios from 'axios'
import {config} from '../util/HostResolver'

export const authApi = {
    getToken,
    getAccount
}

function getToken(login, password) {
    return axiosInstance.post(
        '/token',
        {login, password}
    )
}

function getAccount(token) {
    return axiosInstance.get(
        '/account',
        { headers: {"Authorization" : `${token}`} }
    )
}

const axiosInstance = axios.create({
    baseURL: config.AUTH_BASE_URL
})