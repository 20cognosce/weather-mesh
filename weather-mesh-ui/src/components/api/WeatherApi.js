import axios from 'axios'
import {config} from '../util/HostResolver'

export const weatherApi = {
    getHealth,
    getOptions,
    postInfo
}

function getHealth() {
    return axiosInstanceOrigin.get(
        '/actuator/health'
    )
}

function getOptions(token) {
    return axiosInstance.get(
        '/options',
        {headers: {"Authorization": `${token}`}}
    )
}

function postInfo(token, requestBody) {
    return axiosInstance.post(
        '/info',
        requestBody,
        {headers: {"Authorization": `${token}`}}
    )
}

const axiosInstance = axios.create({
    baseURL: config.WEATHER_ORIGIN + '/weather'
})

const axiosInstanceOrigin = axios.create({
    baseURL: config.WEATHER_ORIGIN
})